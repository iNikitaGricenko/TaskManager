package com.wolfhack.todo.wrapper.swagger;

import com.wolfhack.todo.model.response.ActivityResponseDTO;
import com.wolfhack.todo.wrapper.DomainPage;
import org.springframework.data.domain.Page;

import java.util.List;

public class ActivityPage extends DomainPage<ActivityResponseDTO> {
	public ActivityPage(Page<ActivityResponseDTO> page) {
		super(page);
	}

	public ActivityPage(DomainPage<?> page, List<ActivityResponseDTO> content) {
		super(page, content);
	}
}
