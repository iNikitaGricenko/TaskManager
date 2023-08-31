package com.wolfhack.todo.gateway.database.repository;

import com.wolfhack.todo.gateway.database.model.EntityTaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<EntityTaskTag, Long> {

	void deleteByTag_Id(Long tagId);

}