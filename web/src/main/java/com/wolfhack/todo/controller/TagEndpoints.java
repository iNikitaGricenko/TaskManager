package com.wolfhack.todo.controller;

import com.wolfhack.todo.model.create.TagCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface TagEndpoints {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	long create(@RequestBody TagCreateDTO tagCreateDTO);

}
