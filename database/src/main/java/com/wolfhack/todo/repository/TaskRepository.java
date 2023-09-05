package com.wolfhack.todo.repository;

import com.wolfhack.todo.model.EntityTask;
import com.wolfhack.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<EntityTask, Long> {

	List<EntityTask> findAllByPlannedStartDateIsBetweenAndActualStartDateIsNull(LocalDateTime startDay, LocalDateTime endDay);

	List<EntityTask> findAllByPlannedEndDateIsBetweenAndActualEndDateIsNull(LocalDateTime startDay, LocalDateTime endDay);

}