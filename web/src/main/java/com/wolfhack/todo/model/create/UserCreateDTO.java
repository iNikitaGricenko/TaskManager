package com.wolfhack.todo.model.create;

import jakarta.validation.constraints.*;

import java.io.Serializable;

public record UserCreateDTO(
		@NotNull @NotBlank @NotEmpty String firstName,
		@NotNull @NotBlank @NotEmpty String lastName,
		@NotNull @NotBlank @NotEmpty @Size(min = 5) String username,
		@NotNull @NotBlank @NotEmpty @Size(min = 5) @Email String email,
		@NotNull @NotBlank @NotEmpty @Size(min = 7) String password,
		@NotBlank @NotEmpty String intro,
		@NotBlank @NotEmpty String profile) implements Serializable {
}