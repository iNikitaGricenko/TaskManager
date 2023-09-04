package com.wolfhack.todo.service;

import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.model.Comment;
import com.wolfhack.todo.model.TaskMeta;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Task;
import org.springframework.data.domain.Pageable;

public interface ITaskService {

	Long create(Task task);

	void start(long id);

	Task get(long id);

	long addMeta(long id, TaskMeta taskMeta);

	DomainPage<Task> getPage(Pageable pageable);

	void update(long id, Task task);

	void addTag(long id, long tagId);

	DomainPage<Activity> getActivities(long id, Pageable pageable);

	DomainPage<Comment> getComments(long id, Pageable pageable);

	void assignUser(long id, long userId);

	long finish(long id);

}
