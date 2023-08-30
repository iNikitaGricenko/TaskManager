package com.wolfhack.todo.model.domain;

import lombok.Data;

@Data
public class TaskTag {

	private Tag tag;

	private Task task;

}
