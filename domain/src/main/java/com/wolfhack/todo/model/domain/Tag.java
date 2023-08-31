package com.wolfhack.todo.model.domain;

import com.wolfhack.todo.model.DomainModel;
import lombok.Data;

@Data
public class Tag implements DomainModel {

	private Long id;

	private String title;

	private String slug;

}
