package com.wolfhack.todo.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wolfhack.todo.model.TaskStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponseDTO(
		Long id,
		Long tagId,
		Long userId,
		Long createdBy,
		String title,
		String status,
		String description,
		@JsonFormat(pattern="yyyy-MM-dd") LocalDate createdAt,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime plannedStartDate,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime plannedEndDate,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime actualStartDate,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime actualEndDate) implements Serializable {
}