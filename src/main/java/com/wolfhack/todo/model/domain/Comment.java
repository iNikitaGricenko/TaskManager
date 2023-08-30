package com.wolfhack.todo.model.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

	private Long id;

	private Task task;

	private Activity activity;

	private String title;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private String content;

}
