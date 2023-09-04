package com.wolfhack.todo.service;

import com.wolfhack.todo.model.Tag;
import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.wrapper.DomainPage;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ITaskTagService {

	Long create(Long taskId, Long tagId);

	DomainPage<Task> getTasksByTag(Long tagId, Pageable pageable);

	DomainPage<Tag> getTagsByTasks(Collection<Long> taskIds, Pageable pageable);

	void delete(Long id);

	void deleteByTagId(Long tagId);
}
