package com.wolfhack.todo.adapter.database;

import com.wolfhack.todo.model.Task;

import java.util.List;

public interface TaskDatabaseAdapter extends DatabaseGateway<Task> {

	long update(long id, Task task);

	List<Task> getAllPlannedToStart();

	List<Task> getAllPlannedToEnd();
}
