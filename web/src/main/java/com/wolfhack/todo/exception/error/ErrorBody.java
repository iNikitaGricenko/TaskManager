package com.wolfhack.todo.exception.error;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorBody {
    private final LocalDateTime timestamp = LocalDateTime.now();
    @Schema(example = "400") private final int status;
    @Schema(example = "Bad Request") private final String error;
    @Schema(example = "/api/v1/test") private final String path;
    @Schema(example = "Required String parameter 'name' is not present") private final String message;

    public ErrorBody(HttpStatus httpStatus, HttpServletRequest request, String message) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.path = request.getRequestURI();
        this.message = message;
    }
}
