package com.wolfhack.todo.model;

import com.wolfhack.todo.adapter.DomainModel;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Task implements DomainModel {

	private Long id;

	private User user;

	private User createdBy;

	private List<User> updatedBy;

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

	public void create() {
		this.status = TaskStatus.STARTING;
		this.createdAt = LocalDate.now();
		this.hours = 0.0f;
	}

	public void start() {
		this.status = TaskStatus.IN_PROGRESS;
		this.updatedAt = LocalDate.now();
		this.actualStartDate = LocalDateTime.now();
	}

	public void finish() {
		this.status = TaskStatus.COMPLETED;
		this.actualEndDate = LocalDateTime.now();
		this.hours = Duration.between(this.actualStartDate, this.actualEndDate).toHours();
	}

}
