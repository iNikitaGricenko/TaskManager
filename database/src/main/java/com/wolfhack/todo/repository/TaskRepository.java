package com.wolfhack.todo.repository;

import com.wolfhack.todo.model.EntityTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<EntityTask, Long> {
}