package com.wolfhack.todo.controller;

import com.wolfhack.todo.model.create.UserLoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface SignInEndpoints {

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<?> signIn(@RequestBody UserLoginDTO userLoginDTO);

}
