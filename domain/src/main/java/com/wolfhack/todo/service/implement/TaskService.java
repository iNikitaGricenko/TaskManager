package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.*;
import com.wolfhack.todo.model.DomainPage;
import com.wolfhack.todo.model.domain.Activity;
import com.wolfhack.todo.model.domain.Comment;
import com.wolfhack.todo.model.domain.Task;
import com.wolfhack.todo.model.domain.User;
import com.wolfhack.todo.service.ITaskService;
import com.wolfhack.todo.service.ITaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
		// userDetails = authentication.getPrincipal()

		task.create();
//		task.setCreatedBy(userDetails);
		return taskDatabaseAdapter.save(task);
	}

	@Override
	public Task get(Long id) {
		return taskDatabaseAdapter.getById(id);
	}

	@Override
	public DomainPage<Task> getPage(Pageable pageable) {
		return taskDatabaseAdapter.getPage(pageable);
	}

	@Override
	public void update(Long id, Task task) {
		task.setUpdatedAt(LocalDate.now());
		taskDatabaseAdapter.update(id, task);
	}

	@Override
	public void addTag(Long id, Long tagId) {
		if (!taskDatabaseAdapter.exists(id) && !tagDatabaseAdapter.exists(tagId)) {
			throw new RuntimeException();
		}

		taskTagService.create(id, tagId);
	}

	@Override
	public DomainPage<Activity> getActivities(Long id, Pageable pageable) {
		return activityDatabaseAdapter.getByTask(id, pageable);
	}

	@Override
	public DomainPage<Comment> getComments(Long id, Pageable pageable) {
		return commentDatabaseAdapter.getByTask(id, pageable);
	}

	@Override
	public void assignUser(Long id, Long userId) {
		Task task = get(id);
		User user = userDatabaseAdapter.getById(userId);
		task.setUser(user);
		task.setUpdatedAt(LocalDate.now());
		taskDatabaseAdapter.update(id, task);
	}

	@Override
	public Long finish(Long id) {
		Task task = get(id);
		task.finish();
		task.setUpdatedAt(LocalDate.now());
		return taskDatabaseAdapter.update(id, task);
	}
}
