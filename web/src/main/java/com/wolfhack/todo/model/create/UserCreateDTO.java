package com.wolfhack.todo.model.create;

import java.io.Serializable;

public record UserCreateDTO(
		String firstName,
		String lastName,
		String username,
		String email,
		String password,
		String intro,
		String profile) implements Serializable {
}