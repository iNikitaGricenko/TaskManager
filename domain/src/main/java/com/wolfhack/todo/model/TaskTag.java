package com.wolfhack.todo.model;

import com.wolfhack.todo.adapter.DomainModel;
import lombok.Data;

@Data
public class TaskTag implements DomainModel {

	private Tag tag;

	private Task task;

}
