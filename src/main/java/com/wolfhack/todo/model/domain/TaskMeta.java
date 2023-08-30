package com.wolfhack.todo.model.domain;

import lombok.Data;

@Data
public class TaskMeta {

	private Long id;

	private Task task;

	private String key;

}
