package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.TagDatabaseAdapter;
import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.model.Tag;
import com.wolfhack.todo.service.ITagService;
import com.wolfhack.todo.service.ITaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService {

	private final TagDatabaseAdapter tagDatabaseAdapter;
	private final TaskDatabaseAdapter taskDatabaseAdapter;
	private final ITaskTagService taskTagService;

	@Override
	public Long create(Tag tag) {
		return tagDatabaseAdapter.save(tag);
	}

	@Override
	public void update(Long id, Tag tag) {
		tagDatabaseAdapter.update(id, tag);
	}

	@Override
	public void addTask(Long id, Long taskId) {
		if (!taskDatabaseAdapter.exists(taskId) && !tagDatabaseAdapter.exists(id)) {
			throw new RuntimeException();
		}

		taskTagService.create(taskId, id);
	}

	@Override
	public Tag get(Long id) {
		return tagDatabaseAdapter.getById(id);
	}

	@Override
	public void delete(Long id) {
		tagDatabaseAdapter.delete(id);
		taskTagService.deleteByTagId(id);
	}
}
