package com.wolfhack.todo.model;

import java.io.Serializable;

public record TopicMessagePOJO(String message, String topic) implements Serializable {
}
