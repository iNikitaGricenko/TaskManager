package com.wolfhack.todo.model.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

public record TaskMetaCreateDTO(@NotBlank @NotEmpty String content) implements Serializable {
}