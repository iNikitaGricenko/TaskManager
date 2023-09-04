package com.wolfhack.todo.service;

import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.wrapper.DomainPage;
import org.springframework.data.domain.Pageable;

public interface IActivityService {

	Long create(Long taskId, Activity activity);

	void start(Long id);

	void partialUpdate(Long id, Activity activity);

	void update(Long id, Activity activity);

	Long finish(Long id);

	DomainPage<Activity> getPage(Long taskId, Pageable pageable);

}
