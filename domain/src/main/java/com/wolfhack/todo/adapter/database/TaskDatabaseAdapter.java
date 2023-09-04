package com.wolfhack.todo.adapter.database;

import com.wolfhack.todo.model.Task;

public interface TaskDatabaseAdapter extends DatabaseGateway<Task> {
	long update(long id, Task task);
}
