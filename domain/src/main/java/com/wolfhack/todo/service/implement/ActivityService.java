package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.ActivityDatabaseAdapter;
import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.adapter.database.UserDatabaseAdapter;
import com.wolfhack.todo.exception.NotFoundException;
import com.wolfhack.todo.exception.UnauthorizedException;
import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.model.TaskStatus;
import com.wolfhack.todo.model.User;
import com.wolfhack.todo.service.IActivityService;
import com.wolfhack.todo.wrapper.DomainPage;
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
public class ActivityService implements IActivityService {

	private final ActivityDatabaseAdapter activityDatabaseAdapter;
	private final TaskDatabaseAdapter taskDatabaseAdapter;
	private final UserDatabaseAdapter userDatabaseAdapter;

	@Override
	public Long create(Long taskId, Activity activity) {
		if (!taskDatabaseAdapter.exists(taskId)) {
			throw new NotFoundException("Task does not exist");
		}

		Task task = taskDatabaseAdapter.getById(taskId);
		if (task.getStatus() == TaskStatus.STARTING) {
			task.setStatus(TaskStatus.IN_PROGRESS);
			if (task.getUser() == null) {
				task.setUser(getCurrentUser());
			}
			taskDatabaseAdapter.update(taskId, task);
		}

		activity.create();
		activity.setTaskId(taskId);

		User user = getCurrentUser();
		activity.setCreatedBy(user);
		activity.setUser(user);

		try {
			return activityDatabaseAdapter.save(activity);
		} finally {
			updateTask(activity);
		}
	}

	@Override
	public void start(Long id) {
		Activity activity = activityDatabaseAdapter.getById(id);
		activity.start();

		User user = getCurrentUser();
		activity.setUser(user);

		partialUpdate(id, activity);
		updateTask(activity);
	}

	@Override
	public void partialUpdate(Long id, Activity activity) {
		User user = getCurrentUser();

		activity.setUpdatedAt(LocalDate.now());

		List<User> updatedBy = Optional.ofNullable(activity.getUpdatedBy()).orElse(new LinkedList<>());
		updatedBy.add(user);
		activity.setUpdatedBy(updatedBy);

		activityDatabaseAdapter.partialUpdate(id, activity);
	}

	@Override
	public void update(Long id, Activity activity) {
		User user = getCurrentUser();

		activity.setUpdatedAt(LocalDate.now());

		List<User> updatedBy = Optional.ofNullable(activity.getUpdatedBy()).orElse(new LinkedList<>());
		updatedBy.add(user);
		activity.setUpdatedBy(updatedBy);

		activityDatabaseAdapter.update(id, activity);
	}

	@Override
	public Long finish(Long id) {
		Activity activity = activityDatabaseAdapter.getById(id);
		activity.finish();

		partialUpdate(id, activity);
		updateTask(activity);

		return id;
	}

	@Override
	public DomainPage<Activity> getPage(Long taskId, Pageable pageable) {
		return activityDatabaseAdapter.getByTask(taskId, pageable);
	}

	@Override
	public Activity get(Long id) {
		return activityDatabaseAdapter.getById(id);
	}

	@Override
	public void delete(Long id) {
		if (!activityDatabaseAdapter.exists(id)) {
			throw new NotFoundException("Activity does not exist");
		}
		activityDatabaseAdapter.delete(id);
	}

	private void updateTask(Activity activity) {
		Long taskId = activity.getTaskId();
		Task task = taskDatabaseAdapter.getById(taskId);
		List<User> updatedBy = Optional.ofNullable(task.getUpdatedBy()).orElse(new LinkedList<>());
		updatedBy.add(getCurrentUser());

		task.setUpdatedAt(LocalDate.now());
		taskDatabaseAdapter.partialUpdate(taskId, task);
	}

	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			throw new UnauthorizedException();
		}
		Long principalId = (Long) authentication.getCredentials();
		return userDatabaseAdapter.getById(principalId);
	}
}
