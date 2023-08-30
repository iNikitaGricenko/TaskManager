package com.wolfhack.todo.model.domain;

import com.wolfhack.todo.model.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Task {

	private Long id;

	private User user;

	private User createdBy;

	private User updatedBy;

	private String title;

	private String description;

	private TaskStatus status;

	private float hours;

	private LocalDate createdAt;

	private LocalDate updatedAt;

	private LocalDateTime plannedStartDate;

	private LocalDateTime plannedEndDate;

	private LocalDateTime actualStartDate;

	private LocalDateTime actualEndDate;

	private String content;

}
