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

@Service
@RequiredArgsConstructor
public class ActivityService implements IActivityService {

	private final ActivityDatabaseAdapter activityDatabaseAdapter;
	private final TaskDatabaseAdapter taskDatabaseAdapter;
	private final UserDatabaseAdapter userDatabaseAdapter;

	@Override
	public Long createActivity(Long taskId, Activity activity) {
		if (!taskDatabaseAdapter.exists(taskId)) {
			throw new RuntimeException();
		}
		activity.create();
		activity.setTaskId(taskId);

		return activityDatabaseAdapter.save(activity);
	}

	@Override
	public void startActivity(Long id, Long userId) {
		if (!userDatabaseAdapter.exists(userId)) {
			throw new RuntimeException();
		}
		Activity activity = activityDatabaseAdapter.getById(id);
		activity.start();
		User user = userDatabaseAdapter.getById(userId);
		activity.setUser(user);
		activity.setUpdatedAt(LocalDate.now());

		activityDatabaseAdapter.update(id, activity);
	}

	@Override
	public void updateActivity(Long id, Activity activity) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			throw new RuntimeException();
		}
		// userDetails = authentication.getPrincipal()

		activity.setUpdatedAt(LocalDate.now());
//		activity.setUpdatedBy(userDetails);
		activityDatabaseAdapter.update(id, activity);
	}
}
