package com.wolfhack.todo.controller;

import com.wolfhack.todo.exception.error.ValidationErrorBody;
import com.wolfhack.todo.model.create.UserCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Sign-up endpoint")
public interface SignUpEndpoints {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "400", description = "Request body is invalid", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
	@ApiResponse(responseCode = "201", description = "Registered. Returns Jwt token in header", content = @Content(schema = @Schema(implementation = long.class)))
	@Operation(description = "Register user into application and returns Jwt in header")
	ResponseEntity<?> signUp(@RequestBody UserCreateDTO userCreateDTO);

}
