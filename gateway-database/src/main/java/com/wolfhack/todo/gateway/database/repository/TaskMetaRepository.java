package com.wolfhack.todo.gateway.database.repository;

import com.wolfhack.todo.gateway.database.model.EntityTaskMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskMetaRepository extends JpaRepository<EntityTaskMeta, Long> {
}