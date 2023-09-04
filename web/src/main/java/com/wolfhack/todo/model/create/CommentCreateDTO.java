package com.wolfhack.todo.model.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record CommentCreateDTO(
		String title,
		@NotNull @NotBlank @NotEmpty String content) implements Serializable {
}