package com.wolfhack.todo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFound extends RuntimeException {

	private final String message;
	private final int statusCode;
	private final LocalDateTime timestamp;

	public EntityNotFound() {
		super();
		this.message = "";
		this.statusCode = HttpStatus.NOT_FOUND.value();
		this.timestamp = LocalDateTime.now();
	}

	public EntityNotFound(String message) {
		super(message);
		this.message = message;
		this.statusCode = HttpStatus.NOT_FOUND.value();
		this.timestamp = LocalDateTime.now();
	}
}
