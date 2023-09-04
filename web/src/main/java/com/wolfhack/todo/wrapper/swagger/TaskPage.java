package com.wolfhack.todo.wrapper.swagger;

import com.wolfhack.todo.model.response.TaskResponseDTO;
import com.wolfhack.todo.wrapper.DomainPage;
import org.springframework.data.domain.Page;

import java.util.List;

public class TaskPage extends DomainPage<TaskResponseDTO> {
	public TaskPage(Page<TaskResponseDTO> page) {
		super(page);
	}

	public TaskPage(DomainPage<?> page, List<TaskResponseDTO> content) {
		super(page, content);
	}
}
