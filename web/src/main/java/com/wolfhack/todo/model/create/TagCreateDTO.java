package com.wolfhack.todo.model.create;

import java.io.Serializable;

public record TagCreateDTO(
		String title,
		String slug) implements Serializable {
}