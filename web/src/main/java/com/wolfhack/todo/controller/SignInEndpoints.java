package com.wolfhack.todo.controller;

import com.wolfhack.todo.exception.error.ValidationErrorBody;
import com.wolfhack.todo.model.create.UserLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Sign-in endpoint")
public interface SignInEndpoints {

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "400", description = "Request body is invalid", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
	@ApiResponse(responseCode = "201", description = "Signed in. Returns Jwt token in header", content = @Content(schema = @Schema(implementation = long.class)))
	@Operation(description = "Signs in user into application and returns Jwt in header")
	ResponseEntity<?> signIn(@Valid @RequestBody UserLoginDTO userLoginDTO);

}
