package com.wolfhack.todo.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema
public record ActivityResponseDTO(
		Long id,
		Long userId,
		Long taskId,
		Long createdBy,
		String title,
		String description,
		String status,
		@JsonFormat(pattern="yyyy-MM-dd") LocalDate createdAt,
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime plannedStartDate,
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime plannedEndDate,
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime actualStartDate,
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime actualEndDate) implements Serializable {
}