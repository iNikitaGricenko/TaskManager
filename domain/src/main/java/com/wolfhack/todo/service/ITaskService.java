package com.wolfhack.todo.service;

import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.model.Comment;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Task;
import org.springframework.data.domain.Pageable;

public interface ITaskService {

	Long create(Task task);

	Task get(Long id);

	DomainPage<Task> getPage(Pageable pageable);

	void update(Long id, Task task);

	void addTag(Long id, Long tagId);

	DomainPage<Activity> getActivities(Long id, Pageable pageable);

	DomainPage<Comment> getComments(Long id, Pageable pageable);

	void assignUser(Long id, Long userId);

	Long finish(Long id);

}
