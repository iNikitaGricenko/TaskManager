package com.wolfhack.todo.model.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record TagCreateDTO(
		@NotNull @NotBlank @NotEmpty @Size(min = 2) String title,
		String slug) implements Serializable {
}