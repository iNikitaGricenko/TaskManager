package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.*;
import com.wolfhack.todo.exception.ForbiddenException;
import com.wolfhack.todo.exception.NotFoundException;
import com.wolfhack.todo.exception.UnauthorizedException;
import com.wolfhack.todo.model.*;
import com.wolfhack.todo.service.ITaskMetaService;
import com.wolfhack.todo.service.Messaging;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.service.ITaskService;
import com.wolfhack.todo.service.ITaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

	private final TaskDatabaseAdapter taskDatabaseAdapter;

	private final ActivityDatabaseAdapter activityDatabaseAdapter;

	private final CommentDatabaseAdapter commentDatabaseAdapter;

	private final TagDatabaseAdapter tagDatabaseAdapter;

	private final UserDatabaseAdapter userDatabaseAdapter;

	private final ITaskTagService taskTagService;

	private final ITaskMetaService taskMetaService;

	private final Messaging messaging;

	@Override
	public Long create(Task task) {
		User user = getCurrentUser();

		task.create();
		task.setCreatedBy(user);

		validateAndAssignTaskUser(task);

		return taskDatabaseAdapter.save(task);
	}

	@Override
	public void start(long id) {
		Task task = get(id);

		if (task.getUser() == null) {
			task.setUser(getCurrentUser());
		} else if (!task.getUser().equals(getCurrentUser())) {
			throw new ForbiddenException("You cannot start this task. Only assigned user can start it");
		}

		if (task.getStatus() == TaskStatus.COMPLETED) {
			throw new ForbiddenException("You cannot start task that is already finished");
		}
		if (task.getStatus() == TaskStatus.IN_PROGRESS) {
			throw new ForbiddenException("You cannot start task that is already started");
		}

		task.start();
		update(id, task);
	}

	@Override
	public Task get(long id) {
		return taskDatabaseAdapter.getById(id);
	}

	@Override
	public long addMeta(long id, TaskMeta taskMeta) {
		if (!taskDatabaseAdapter.exists(id)) {
			throw new NotFoundException();
		}

		Long metaId = taskMetaService.create(taskMeta);
		return taskMetaService.assignTask(id, metaId);
	}

	@Override
	public DomainPage<Task> getPage(Pageable pageable) {
		return taskDatabaseAdapter.getPage(pageable);
	}

	@Override
	public void update(long id, Task task) {
		User user = getCurrentUser();

		List<User> updatedBy = Optional.ofNullable(task.getUpdatedBy()).orElse(new LinkedList<>());
		updatedBy.add(user);

		task.setUpdatedBy(updatedBy);
		task.setUpdatedAt(LocalDate.now());
		taskDatabaseAdapter.partialUpdate(id, task);
	}

	@Override
	public void addTag(long id, long tagId) {
		if (!taskDatabaseAdapter.exists(id) && !tagDatabaseAdapter.exists(tagId)) {
			taskDatabaseAdapter.delete(id);
			throw new NotFoundException();
		}

		taskTagService.create(id, tagId);
	}

	@Override
	public DomainPage<Activity> getActivities(long id, Pageable pageable) {
		return activityDatabaseAdapter.getByTask(id, pageable);
	}

	@Override
	public DomainPage<Comment> getComments(long id, Pageable pageable) {
		return commentDatabaseAdapter.getByTask(id, pageable);
	}

	@Override
	public void assignUser(long id, long userId) {
		Task task = get(id);
		User user = userDatabaseAdapter.getById(userId);
		task.setUser(user);
		task.setUpdatedAt(LocalDate.now());
		taskDatabaseAdapter.partialUpdate(id, task);
	}

	@Override
	public void unassign(long id) {
		Task task = get(id);
		task.setUser(null);
		task.setUpdatedAt(LocalDate.now());
		taskDatabaseAdapter.update(id, task);
	}

	@Override
	public long finish(long id) {
		Task task = get(id);

		if (task.getStatus() == TaskStatus.STARTING) {
			throw new ForbiddenException("You cannot finish task that is not started");
		}
		if (task.getStatus() == TaskStatus.COMPLETED) {
			throw new ForbiddenException("You cannot finish task that is already finished");
		}

		task.finish();
		task.setUpdatedAt(LocalDate.now());
		return taskDatabaseAdapter.partialUpdate(id, task);
	}

	@Override
	public void delete(Long id) {
		if (!taskDatabaseAdapter.exists(id)) {
			throw new NotFoundException("Task does not exist");
		}
		taskDatabaseAdapter.delete(id);
	}

	@Scheduled(timeUnit = TimeUnit.DAYS, fixedRate = 1)
	public void sendNotificationToStart() {
		taskDatabaseAdapter.getAllPlannedToStart().forEach(this::sendNotificationToStart);
	}

	@Scheduled(timeUnit = TimeUnit.DAYS, fixedRate = 1)
	public void sendNotificationToEnd() {
		taskDatabaseAdapter.getAllPlannedToEnd().forEach(this::sendNotificationToEnd);
	}

	private void sendNotificationToStart(Task plannedToStart) {
		String taskTitle = plannedToStart.getTitle();
		String tagTitle = tagDatabaseAdapter.getById(plannedToStart.getTagId()).getTitle();

		String message = "(%s) Task %s should be started!".formatted(tagTitle, taskTitle);

		TopicMessagePOJO messageBody = new TopicMessagePOJO(message, tagTitle);
		messaging.send(messageBody);

		User user = plannedToStart.getUser();
		if (user == null) {
			return;
		}

		String subject = "Task %s planned to start now";
		message = message + " Please start to work on task";

		new MailMessagePOJO(user.getEmail(), subject, message);
	}

	private void sendNotificationToEnd(Task plannedToEnd) {
		String taskTitle = plannedToEnd.getTitle();
		String tagTitle = tagDatabaseAdapter.getById(plannedToEnd.getTagId()).getTitle();

		String message = "(%s) Task %s should be finished!".formatted(tagTitle, taskTitle);

		TopicMessagePOJO messageBody = new TopicMessagePOJO(message, tagTitle);
		messaging.send(messageBody);

		User user = plannedToEnd.getUser();
		if (user == null) {
			return;
		}

		String subject = "Task %s should already finished";
		message = message + " Please finish task";

		new MailMessagePOJO(user.getEmail(), subject, message);
	}


	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			throw new UnauthorizedException();
		}
		Long principalId = (Long) authentication.getCredentials();
		return userDatabaseAdapter.getById(principalId);
	}

	private void validateAndAssignTaskUser(Task task) {
		User taskUser = task.getUser();
		if (taskUser != null) {
			if (taskUser.getId() == null) {

				if (taskUser.haveUsername()) {
					User byUsername = userDatabaseAdapter.getByUsername(taskUser.getUsername());
					task.setUser(byUsername);
				} else if (taskUser.haveEmail()) {
					User byEmail = userDatabaseAdapter.getByEmail(taskUser.getEmail());
					task.setUser(byEmail);
				} else {
					task.setUser(null);
				}

			}
		}
	}
}
