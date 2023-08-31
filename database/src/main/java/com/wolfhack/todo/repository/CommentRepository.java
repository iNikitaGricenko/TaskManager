package com.wolfhack.todo.repository;

import com.wolfhack.todo.model.EntityComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<EntityComment, Long> {

	Page<EntityComment> findByTask_Id(Long id, Pageable pageable);

}