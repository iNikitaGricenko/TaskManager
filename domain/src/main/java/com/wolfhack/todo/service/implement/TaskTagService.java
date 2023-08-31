package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.TagDatabaseAdapter;
import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.adapter.database.TaskTagDatabaseAdapter;
import com.wolfhack.todo.model.domain.Tag;
import com.wolfhack.todo.model.domain.Task;
import com.wolfhack.todo.model.domain.TaskTag;
import com.wolfhack.todo.service.ITaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskTagService implements ITaskTagService {

	private final TaskTagDatabaseAdapter taskTagDatabaseAdapter;
	private final TagDatabaseAdapter tagDatabaseAdapter;
	private final TaskDatabaseAdapter taskDatabaseAdapter;

	@Override
	public Long create(Long taskId, Long tagId) {
		Task task = taskDatabaseAdapter.getById(taskId);
		Tag tag = tagDatabaseAdapter.getById(tagId);

		TaskTag taskTag = new TaskTag();
		taskTag.setTask(task);
		taskTag.setTag(tag);

		return taskTagDatabaseAdapter.save(taskTag);
	}

	@Override
	public void delete(Long id) {
		taskTagDatabaseAdapter.delete(id);
	}

	@Override
	public void deleteByTagId(Long tagId) {
		taskTagDatabaseAdapter.deleteByTagId(tagId);
	}
}
