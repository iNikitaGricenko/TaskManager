package com.wolfhack.todo.model.create;

import java.io.Serializable;

public record CommentCreateDTO(
		String title,
		String content) implements Serializable {
}