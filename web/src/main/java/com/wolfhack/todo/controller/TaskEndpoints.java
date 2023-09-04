package com.wolfhack.todo.controller;

import com.wolfhack.todo.model.create.CommentCreateDTO;
import com.wolfhack.todo.model.create.TaskCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface TaskEndpoints {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	long create(@RequestBody TaskCreateDTO taskCreateDTO);

	@PostMapping("/comment/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	long comment(@PathVariable Long id, @RequestBody CommentCreateDTO commentCreateDTO);

	@PatchMapping("/assign/{taskId}/{userId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	void assign(@PathVariable Long taskId, @PathVariable Long userId);

	@PostMapping("/start/{id}")
	@ResponseStatus(HttpStatus.OK)
	void start(@PathVariable Long id);

	@DeleteMapping("/finish/{id}")
	@ResponseStatus(HttpStatus.OK)
	void finish(@PathVariable Long id);

}
