package com.wolfhack.todo.model;

import java.io.Serializable;

/**
 * DTO for {@link com.wolfhack.todo.model.EntityTaskMeta}
 */
public record CreateTaskMetaDTO(String content) implements Serializable {
}