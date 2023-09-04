package com.wolfhack.todo.service;

import com.wolfhack.todo.model.Activity;

public interface IActivityService {

	Long create(Long taskId, Activity activity);

	void start(Long id);

	void update(Long id, Activity activity);

	Long finish(Long id);

}
