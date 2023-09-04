package com.wolfhack.todo.controller;

import com.wolfhack.todo.model.create.UserCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface SignUpEndpoints {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<?> signUp(@RequestBody UserCreateDTO userCreateDTO);

}
