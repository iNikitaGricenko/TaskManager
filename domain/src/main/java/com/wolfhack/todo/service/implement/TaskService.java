package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.*;
import com.wolfhack.todo.security.model.UserSecurity;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.model.Comment;
import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.model.User;
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

	@Override
	public Long create(Task task) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			throw new RuntimeException();
		}
		Long principalId = (Long) authentication.getCredentials();
		User user = userDatabaseAdapter.getById(principalId);

		task.create();
		task.setCreatedBy(user);
		return taskDatabaseAdapter.save(task);
	}

	@Override
	public void start(long id) {
		Task task = get(id);
		task.start();
		update(id, task);
	}

	@Override
	public Task get(long id) {
		return taskDatabaseAdapter.getById(id);
	}

	@Override
	public DomainPage<Task> getPage(Pageable pageable) {
		return taskDatabaseAdapter.getPage(pageable);
	}

	@Override
	public void update(long id, Task task) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			throw new RuntimeException();
		}
		Long principalId = (Long) authentication.getCredentials();
		User user = userDatabaseAdapter.getById(principalId);

		List<User> updatedBy = Optional.ofNullable(task.getUpdatedBy()).orElse(new LinkedList<>());
		updatedBy.add(user);

		task.setUpdatedBy(updatedBy);
		task.setUpdatedAt(LocalDate.now());
		taskDatabaseAdapter.update(id, task);
	}

	@Override
	public void addTag(long id, long tagId) {
		if (!taskDatabaseAdapter.exists(id) && !tagDatabaseAdapter.exists(tagId)) {
			throw new RuntimeException();
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
		taskDatabaseAdapter.update(id, task);
	}

	@Override
	public long finish(long id) {
		Task task = get(id);
		task.finish();
		task.setUpdatedAt(LocalDate.now());
		return taskDatabaseAdapter.update(id, task);
	}
}
