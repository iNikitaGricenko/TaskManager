package com.wolfhack.todo.repository;

import com.wolfhack.todo.model.EntityTaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<EntityTaskTag, Long> {

	void deleteByTag_Id(Long tagId);

}