package com.wolfhack.todo.model;

import com.wolfhack.todo.adapter.DomainModel;
import lombok.Data;

@Data
public class Tag implements DomainModel {

	private Long id;

	private String title;

	private String slug;

}
