package com.wolfhack.todo.controller;

import com.wolfhack.todo.model.create.TaskMetaCreateDTO;
import com.wolfhack.todo.model.create.CommentCreateDTO;
import com.wolfhack.todo.model.create.TaskCreateDTO;
import com.wolfhack.todo.model.response.TaskResponseDTO;
import com.wolfhack.todo.wrapper.DomainPage;
import org.springframework.data.domain.Pageable;
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

	@DeleteMapping("/assign/{taskId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	void unassign(@PathVariable Long taskId);

	@PostMapping("/start/{id}")
	@ResponseStatus(HttpStatus.OK)
	void start(@PathVariable Long id);

	@PostMapping("/meta/{id}")
	@ResponseStatus(HttpStatus.OK)
	long addMeta(@PathVariable Long id, @RequestBody TaskMetaCreateDTO taskMetaDTO);

	@DeleteMapping("/finish/{id}")
	@ResponseStatus(HttpStatus.OK)
	void finish(@PathVariable Long id);

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable Long id);

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	DomainPage<TaskResponseDTO> getPage(Pageable pageable);

}
