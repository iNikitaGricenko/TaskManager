package com.wolfhack.todo.service;

public interface ITaskTagService {

	Long create(Long taskId, Long tagId);

	void delete(Long id);

	void deleteByTagId(Long tagId);
}
