package com.wolfhack.todo.controller;

import com.wolfhack.todo.model.create.UserLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface SignInEndpoints {

	@PostMapping
	ResponseEntity<?> signIn(@RequestBody UserLoginDTO userLoginDTO);

}
