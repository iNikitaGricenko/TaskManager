package com.wolfhack.todo.model;

import com.wolfhack.todo.adapter.DomainModel;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
public class Activity implements DomainModel {

	private Long id;

	private User user;

	private Long taskId;

	private User createdBy;

	private List<User> updatedBy = new LinkedList<>();

	private String title;

	private String description;

	private ActivityStatus status;

	private float hours;

	private LocalDate createdAt;

	private LocalDate updatedAt;

	private LocalDateTime plannedStartDate;

	private LocalDateTime plannedEndDate;

	private LocalDateTime actualStartDate;

	private LocalDateTime actualEndDate;

	public void create() {
		this.status = ActivityStatus.OPEN;
		this.createdAt = LocalDate.now();
		this.hours = 0.0f;
	}

	public void start() {
		this.status = ActivityStatus.STARTED;
		this.updatedAt = LocalDate.now();
		this.actualStartDate = LocalDateTime.now();
	}

	public void finish() {
		this.status = ActivityStatus.DONE;
		this.actualEndDate = LocalDateTime.now();
		this.hours = Duration.between(this.actualStartDate, this.actualEndDate).toHours();
	}

}
