package com.wolfhack.todo.model.domain;

import com.wolfhack.todo.model.DomainModel;
import lombok.Data;

@Data
public class TaskMeta implements DomainModel {

	private Long id;

	private Task task;

	private String key;

}
