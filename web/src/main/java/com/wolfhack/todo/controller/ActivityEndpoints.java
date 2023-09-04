package com.wolfhack.todo.controller;

import com.wolfhack.todo.model.create.ActivityCreateDTO;
import com.wolfhack.todo.model.response.ActivityResponseDTO;
import com.wolfhack.todo.model.update.ActivityUpdateDTO;
import com.wolfhack.todo.model.create.CommentCreateDTO;
import com.wolfhack.todo.wrapper.DomainPage;
import org.springframework.data.domain.Pageable;
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

	@PatchMapping("/{activityId}")
	@ResponseStatus(HttpStatus.OK)
	void partialUpdate(@PathVariable Long activityId, @RequestBody ActivityUpdateDTO activityUpdateDTO);

	@PutMapping("/{activityId}")
	@ResponseStatus(HttpStatus.OK)
	void update(@PathVariable Long activityId, @RequestBody ActivityCreateDTO activityUpdateDTO);

	@GetMapping("/{taskId}")
	@ResponseStatus(HttpStatus.OK)
	DomainPage<ActivityResponseDTO> getPage(@PathVariable Long taskId, Pageable pageable);

}
