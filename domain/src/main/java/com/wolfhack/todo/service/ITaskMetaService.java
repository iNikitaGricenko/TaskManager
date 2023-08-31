package com.wolfhack.todo.service;

import com.wolfhack.todo.model.domain.TaskMeta;

public interface ITaskMetaService {

	Long create(TaskMeta taskMeta);

	void update(Long id, TaskMeta taskMeta);

	Long assignTask(Long id, Long taskId);

}
