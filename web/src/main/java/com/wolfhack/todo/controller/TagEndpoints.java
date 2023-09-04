package com.wolfhack.todo.controller;

import com.wolfhack.todo.model.create.TagCreateDTO;
import com.wolfhack.todo.model.response.TaskResponseDTO;
import com.wolfhack.todo.wrapper.DomainPage;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface TagEndpoints {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	long create(@RequestBody TagCreateDTO tagCreateDTO);

	@GetMapping("/task/{tagId}")
	@ResponseStatus(HttpStatus.OK)
	DomainPage<TaskResponseDTO> getTasksByTag(@PathVariable Long tagId, Pageable pageable);

}
