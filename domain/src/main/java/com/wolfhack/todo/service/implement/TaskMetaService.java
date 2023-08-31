package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.adapter.database.TaskMetaDatabaseAdapter;
import com.wolfhack.todo.model.domain.Task;
import com.wolfhack.todo.model.domain.TaskMeta;
import com.wolfhack.todo.service.ITaskMetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskMetaService implements ITaskMetaService {

	private final TaskMetaDatabaseAdapter taskMetaDatabaseAdapter;
	private final TaskDatabaseAdapter taskDatabaseAdapter;

	@Override
	public Long create(TaskMeta taskMeta) {
		return taskMetaDatabaseAdapter.save(taskMeta);
	}

	@Override
	public void update(Long id, TaskMeta taskMeta) {
		taskMetaDatabaseAdapter.update(id, taskMeta);
	}

	@Override
	public Long assignTask(Long id, Long taskId) {
		TaskMeta taskMeta = taskMetaDatabaseAdapter.getById(id);
		Task task = taskDatabaseAdapter.getById(taskId);
		taskMeta.setTask(task);

		return taskMetaDatabaseAdapter.update(id, taskMeta);
	}
}
