package com.wolfhack.todo.exception.error;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;

import java.time.LocalDateTime;

@Data
public class ErrorBody {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String path;
    private final String message;

    public ErrorBody(HttpStatus httpStatus, ServerHttpRequest request, String message) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.path = request.getURI().toString();
        this.message = message;
    }
}
