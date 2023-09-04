package com.wolfhack.todo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatus {

	STARTING("Starting"),
	IN_PROGRESS("In Progress"),
	COMPLETED("Completed"),
	PAUSED("Paused"),
	CANCELLED("Cancelled");

	private final String title;

}
