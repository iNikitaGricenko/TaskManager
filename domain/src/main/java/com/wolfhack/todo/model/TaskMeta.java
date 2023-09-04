package com.wolfhack.todo.model;

import com.wolfhack.todo.adapter.DomainModel;
import lombok.Data;

@Data
public class TaskMeta implements DomainModel {

	private Long id;

	private Task task;

	private String key;

	private String content;

}
