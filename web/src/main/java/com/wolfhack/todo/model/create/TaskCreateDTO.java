package com.wolfhack.todo.model.create;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public record TaskCreateDTO(
		Long userId,
		String userUsername,
		String userEmail,
		String title,
		String description,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime plannedStartDate,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime plannedEndDate) implements Serializable {
}