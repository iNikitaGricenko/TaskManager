package com.wolfhack.todo.model.response;

import java.io.Serializable;

public record TagResponseDTO(
		Long id,
		String title,
		String slug) implements Serializable {
}