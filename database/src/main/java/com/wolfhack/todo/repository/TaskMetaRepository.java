package com.wolfhack.todo.repository;

import com.wolfhack.todo.model.EntityTaskMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskMetaRepository extends JpaRepository<EntityTaskMeta, Long> {
}