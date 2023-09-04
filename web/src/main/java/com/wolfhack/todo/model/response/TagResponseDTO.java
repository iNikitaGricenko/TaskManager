package com.wolfhack.todo.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema
public record TagResponseDTO(
		Long id,
		String title,
		String slug) implements Serializable {
}