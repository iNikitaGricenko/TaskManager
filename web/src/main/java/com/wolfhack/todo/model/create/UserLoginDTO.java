package com.wolfhack.todo.model.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record UserLoginDTO(
		@NotBlank @NotEmpty @Size(min = 5) String username,
		@NotBlank @NotEmpty @Size(min = 7) String password) implements Serializable {
}