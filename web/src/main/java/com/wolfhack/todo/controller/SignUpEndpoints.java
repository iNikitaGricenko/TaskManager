package com.wolfhack.todo.controller;

import com.wolfhack.todo.model.create.UserCreateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface SignUpEndpoints {

	@PostMapping
	ResponseEntity<?> signUp(@RequestBody UserCreateDTO userCreateDTO);

}
