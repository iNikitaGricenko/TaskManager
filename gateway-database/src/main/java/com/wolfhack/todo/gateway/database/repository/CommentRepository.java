package com.wolfhack.todo.gateway.database.repository;

import com.wolfhack.todo.gateway.database.model.EntityComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<EntityComment, Long> {

	Page<EntityComment> findByTask_Id(Long id, Pageable pageable);

}