package com.wolfhack.todo.model.create;

import java.io.Serializable;
import java.time.LocalDateTime;

public record TaskCreateDTO(
		Long userId,
		String userUsername,
		String userEmail,
		String title,
		String description,
		LocalDateTime plannedStartDate,
		LocalDateTime plannedEndDate) implements Serializable {
}