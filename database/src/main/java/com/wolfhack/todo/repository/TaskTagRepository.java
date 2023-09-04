package com.wolfhack.todo.repository;

import com.wolfhack.todo.model.EntityTaskTag;
import com.wolfhack.todo.model.Tag;
import com.wolfhack.todo.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TaskTagRepository extends JpaRepository<EntityTaskTag, Long> {

	void deleteByTag_Id(Long tagId);

	Page<EntityTaskTag> findByTag_Id(Long tagId, Pageable pageable);

	Page<EntityTaskTag> findAllByTask_IdIn(Collection<Long> taskIds, Pageable pageable);
}