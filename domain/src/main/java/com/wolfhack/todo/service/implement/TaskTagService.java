package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.TagDatabaseAdapter;
import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.adapter.database.TaskTagDatabaseAdapter;
import com.wolfhack.todo.model.Tag;
import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.model.TaskTag;
import com.wolfhack.todo.service.ITaskTagService;
import com.wolfhack.todo.wrapper.DomainPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
	public DomainPage<Task> getTasksByTag(Long tagId, Pageable pageable) {
		return taskTagDatabaseAdapter.getByTag(tagId, pageable);
	}

	@Override
	public DomainPage<Tag> getTagsByTasks(Collection<Long> taskIds, Pageable pageable) {
		return taskTagDatabaseAdapter.getByTask(taskIds, pageable);
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
