package com.wolfhack.todo.repository;

import com.wolfhack.todo.model.EntityActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<EntityActivity, Long> {

	Page<EntityActivity> findByTask_Id(Long taskId, Pageable pageable);

}