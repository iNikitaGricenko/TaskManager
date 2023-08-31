package com.wolfhack.todo.gateway.database.repository;

import com.wolfhack.todo.gateway.database.model.EntityTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<EntityTag, Long> {
}