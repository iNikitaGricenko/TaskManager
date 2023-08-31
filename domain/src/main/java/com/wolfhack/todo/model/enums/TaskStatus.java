package com.wolfhack.todo.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatus {

	STARTING, IN_PROGRESS, COMPLETED, PAUSED, CANCELLED

}
