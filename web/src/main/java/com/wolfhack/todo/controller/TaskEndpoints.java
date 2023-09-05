package com.wolfhack.todo.controller;

import com.wolfhack.todo.exception.error.ErrorBody;
import com.wolfhack.todo.exception.error.ValidationErrorBody;
import com.wolfhack.todo.model.create.CommentCreateDTO;
import com.wolfhack.todo.model.create.TaskCreateDTO;
import com.wolfhack.todo.model.create.TaskMetaCreateDTO;
import com.wolfhack.todo.model.response.TaskResponseDTO;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.wrapper.swagger.TaskPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Task endpoints")
public interface TaskEndpoints {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "400",
			description = "Request body is invalid",
			content = @Content(
					schema = @Schema(
							implementation = ValidationErrorBody.class)))
	@ApiResponse(responseCode = "201",
			description = "Created and returned id",
			content = @Content(
					schema = @Schema(
							implementation = long.class)))
	@Operation(description = "Creates new task")
	long create(@Valid @RequestBody TaskCreateDTO taskCreateDTO);

	@PostMapping("/comment/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "404",
			description = "Task not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "400",
			description = "Request body is invalid",
			content = @Content(
					schema = @Schema(
							implementation = ValidationErrorBody.class)))
	@ApiResponse(responseCode = "201",
			description = "Created and returned id of comment",
			content = @Content(
					schema = @Schema(
							implementation = long.class)))
	@Operation(description = "Add comment to tas (Logged in user will be assigned to comment)")
	long comment(@PathVariable Long id, @Valid @RequestBody CommentCreateDTO commentCreateDTO);

	@PatchMapping("/assign/{taskId}/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Task or user not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200",
			description = "Updated",
			content = @Content(
					schema = @Schema(
							implementation = void.class)))
	@Operation(description = "Assign user to task")
	void assign(@PathVariable Long taskId, @PathVariable Long userId);

	@DeleteMapping("/assign/{taskId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Task not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200",
			description = "Updated",
			content = @Content(
					schema = @Schema(
							implementation = void.class)))
	@Operation(description = "Unassign user from task")
	void unassign(@PathVariable Long taskId);

	@PostMapping("/start/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Task not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200",
			description = "Updated",
			content = @Content(
					schema = @Schema(
							implementation = void.class)))
	@Operation(description = "Starts task (If no user assigned to the task, then logged in user will be assigned)")
	void start(@PathVariable Long id);

	@PostMapping("/meta/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Task not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "400",
			description = "Request body is invalid",
			content = @Content(
					schema = @Schema(
							implementation = ValidationErrorBody.class)))
	@ApiResponse(responseCode = "200",
			description = "Created and returned id of meta",
			content = @Content(
					schema = @Schema(
							implementation = long.class)))
	@Operation(description = "Adds meta to task (additional information)")
	long addMeta(@PathVariable Long id, @Valid @RequestBody TaskMetaCreateDTO taskMetaDTO);

	@PostMapping("/finish/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Task not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200",
			description = "Updated",
			content = @Content(
					schema = @Schema(
							implementation = void.class)))
	@Operation(description = "Finishes task")
	void finish(@PathVariable Long id);

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Task not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200",
			description = "Deleted",
			content = @Content(
					schema = @Schema(
							implementation = void.class)))
	@Operation(description = "Fully removes task")
	void delete(@PathVariable Long id);

	@GetMapping
	@PageableAsQueryParam
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "200",
			content = @Content(
					schema = @Schema(
							implementation = TaskPage.class)))
	@Operation(description = "returns page of task")
	DomainPage<TaskResponseDTO> getPage(Pageable pageable);

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Activity not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200",
			content = @Content(
					schema = @Schema(
							implementation = TaskResponseDTO.class)))
	@Operation(description = "returns task")
	TaskResponseDTO getOne(@PathVariable Long id);

}
