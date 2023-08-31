package com.wolfhack.todo.gateway.database.repository;

import com.wolfhack.todo.gateway.database.model.EntityActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<EntityActivity, Long> {

	Page<EntityActivity> findByTask_Id(Long taskId, Pageable pageable);

}