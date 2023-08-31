package com.wolfhack.todo.service;

import com.wolfhack.todo.model.domain.Tag;

public interface ITagService {

	Long create(Tag tag);

	void update(Long id, Tag tag);

	void addTask(Long id, Long taskId);

	Tag get(Long id);

	void delete(Long id);

}