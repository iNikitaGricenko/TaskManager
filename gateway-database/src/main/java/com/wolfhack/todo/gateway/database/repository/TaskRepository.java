package com.wolfhack.todo.gateway.database.repository;

import com.wolfhack.todo.gateway.database.model.EntityTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<EntityTask, Long> {
}