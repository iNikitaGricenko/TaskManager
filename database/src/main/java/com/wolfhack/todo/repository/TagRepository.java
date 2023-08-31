package com.wolfhack.todo.repository;

import com.wolfhack.todo.model.EntityTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<EntityTag, Long> {
}