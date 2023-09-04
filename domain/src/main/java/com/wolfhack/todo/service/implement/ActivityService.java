package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.ActivityDatabaseAdapter;
import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.adapter.database.UserDatabaseAdapter;
import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.model.User;
import com.wolfhack.todo.service.IActivityService;
import lombok.RequiredArgsConstructor;
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
			throw new RuntimeException();
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			throw new RuntimeException();
		}

		activity.create();
		activity.setTaskId(taskId);

		Long principalId = (Long) authentication.getCredentials();
		User user = userDatabaseAdapter.getById(principalId);

		activity.setCreatedBy(user);

		return activityDatabaseAdapter.save(activity);
	}

	@Override
	public void start(Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			throw new RuntimeException();
		}

		Activity activity = activityDatabaseAdapter.getById(id);
		activity.start();

		Long principalId = (Long) authentication.getCredentials();
		User user = userDatabaseAdapter.getById(principalId);
		activity.setUser(user);

		activity.setUpdatedAt(LocalDate.now());

		activityDatabaseAdapter.update(id, activity);
	}

	@Override
	public void update(Long id, Activity activity) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			throw new RuntimeException();
		}
		Long principalId = (Long) authentication.getCredentials();
		User user = userDatabaseAdapter.getById(principalId);

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
		update(id, activity);
		return id;
	}
}
