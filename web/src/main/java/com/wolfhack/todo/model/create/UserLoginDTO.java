package com.wolfhack.todo.model.create;

import java.io.Serializable;

public record UserLoginDTO(
		String username,
		String password) implements Serializable {
}