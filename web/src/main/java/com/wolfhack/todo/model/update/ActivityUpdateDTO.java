package com.wolfhack.todo.model.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ActivityUpdateDTO(
		@NotNull @NotBlank @NotEmpty @Size(min = 4) String title,
    String description,
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime plannedStartDate,
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime plannedEndDate) implements Serializable {
}