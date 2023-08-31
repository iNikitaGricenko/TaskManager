package com.wolfhack.todo.model.domain;

import com.wolfhack.todo.model.DomainModel;
import lombok.Data;

@Data
public class TaskTag implements DomainModel {

	private Tag tag;

	private Task task;

}
