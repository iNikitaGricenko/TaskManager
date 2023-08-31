package com.wolfhack.todo.service;

import com.wolfhack.todo.model.Activity;

public interface IActivityService {

	Long createActivity(Long taskId, Activity activity);

	void startActivity(Long id, Long userId);

	void updateActivity(Long id, Activity activity);

}
