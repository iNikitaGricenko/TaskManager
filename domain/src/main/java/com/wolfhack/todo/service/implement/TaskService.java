package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.*;
import com.wolfhack.todo.exception.ForbiddenException;
import com.wolfhack.todo.exception.NotFoundException;
import com.wolfhack.todo.exception.UnauthorizedException;
import com.wolfhack.todo.model.*;
import com.wolfhack.todo.service.ITaskMetaService;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.service.ITaskService;
import com.wolfhack.todo.service.ITaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
			throw new ForbiddenException("You cannot start a task. Only assigned user can start it");
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
