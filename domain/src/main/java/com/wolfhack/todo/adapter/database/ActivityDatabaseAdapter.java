package com.wolfhack.todo.adapter.database;

import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Activity;
import org.springframework.data.domain.Pageable;

public interface ActivityDatabaseAdapter extends DatabaseGateway<Activity> {

	Long update(Long id, Activity model);

	DomainPage<Activity> getByTask(Long taskId, Pageable pageable);

}
