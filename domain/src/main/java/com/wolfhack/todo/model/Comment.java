package com.wolfhack.todo.model;

import com.wolfhack.todo.adapter.DomainModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment implements DomainModel {

	private Long id;

	private Task task;

	private Activity activity;

	private String title;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private String content;

}
