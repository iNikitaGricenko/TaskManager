package com.wolfhack.todo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityStatus {

	STARTED("Started"),
	DONE("Done"),
	OPEN("Open"),
	CANCELLED("Cancelled");

	private final String title;

}
