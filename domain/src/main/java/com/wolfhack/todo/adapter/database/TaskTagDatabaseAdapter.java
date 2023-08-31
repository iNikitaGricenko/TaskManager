package com.wolfhack.todo.adapter.database;

import com.wolfhack.todo.model.TaskTag;

public interface TaskTagDatabaseAdapter extends DatabaseGateway<TaskTag> {
	void deleteByTagId(Long tagId);
}
