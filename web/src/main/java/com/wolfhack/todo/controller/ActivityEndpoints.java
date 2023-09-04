package com.wolfhack.todo.controller;

import com.wolfhack.todo.model.create.ActivityCreateDTO;
import com.wolfhack.todo.model.create.CommentCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface ActivityEndpoints {

	@PostMapping("/{taskId}")
	@ResponseStatus(HttpStatus.CREATED)
	long create(@PathVariable Long taskId, @RequestBody ActivityCreateDTO activityCreateDTO);

	@PostMapping("/comment/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	long comment(@PathVariable Long id, @RequestBody CommentCreateDTO commentCreateDTO);

	@PostMapping("/start/{id}")
	@ResponseStatus(HttpStatus.OK)
	void start(@PathVariable Long id);

	@DeleteMapping("/finish/{id}")
	@ResponseStatus(HttpStatus.OK)
	void finish(@PathVariable Long id);

}
