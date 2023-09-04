package com.wolfhack.todo.model.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public record TaskCreateDTO(
		@NotNull @Min(1) Long tagId,
		Long userId,
		String userUsername,
		String userEmail,
		@NotNull @NotBlank @NotEmpty @Size(min = 4) String title,
		String description,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime plannedStartDate,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime plannedEndDate) implements Serializable {
}