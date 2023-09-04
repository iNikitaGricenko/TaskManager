package com.wolfhack.todo.adapter.database;

import com.wolfhack.todo.model.Tag;
import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.model.TaskTag;
import com.wolfhack.todo.wrapper.DomainPage;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface TaskTagDatabaseAdapter extends DatabaseGateway<TaskTag> {
	void deleteByTagId(Long tagId);

	DomainPage<Task> getByTag(Long tagId, Pageable pageable);

	DomainPage<Tag> getByTask(Collection<Long> taskIds, Pageable pageable);
}
