package com.wolfhack.todo.controller;

import com.wolfhack.todo.exception.error.ErrorBody;
import com.wolfhack.todo.exception.error.ValidationErrorBody;
import com.wolfhack.todo.model.create.ActivityCreateDTO;
import com.wolfhack.todo.model.create.CommentCreateDTO;
import com.wolfhack.todo.model.response.ActivityResponseDTO;
import com.wolfhack.todo.model.update.ActivityUpdateDTO;
import com.wolfhack.todo.wrapper.swagger.ActivityPage;
import com.wolfhack.todo.wrapper.DomainPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Activity endpoints")
public interface ActivityEndpoints {

	@PostMapping("/{taskId}")
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
			description = "Created and returned id",
			content = @Content(
					schema = @Schema(
							implementation = long.class)))
	@Operation(description = "Create activity in task (Logged in user will be assigned to this activity)")
	long create(@PathVariable Long taskId, @RequestBody ActivityCreateDTO activityCreateDTO);

	@PostMapping("/comment/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "404",
			description = "Activity not found",
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
	@Operation(description = "Add comment to activity (Logged in user will be assigned to comment)")
	long comment(@PathVariable Long id, @RequestBody CommentCreateDTO commentCreateDTO);

	@PostMapping("/start/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Activity not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200",
			description = "Updated",
			content = @Content(
					schema = @Schema(
							implementation = void.class)))
	@Operation(description = "Starts activity (Logged in user will be assigned)")
	void start(@PathVariable Long id);

	@DeleteMapping("/finish/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Activity not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200",
			description = "Updated",
			content = @Content(
					schema = @Schema(
							implementation = void.class)))
	@Operation(description = "Finishes activity")
	void finish(@PathVariable Long id);

	@PatchMapping("/{activityId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Activity not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "400",
			description = "Request body is invalid",
			content = @Content(
					schema = @Schema(
							implementation = ValidationErrorBody.class)))
	@ApiResponse(responseCode = "200",
			description = "Updated",
			content = @Content(
					schema = @Schema(
							implementation = void.class)))
	@Operation(description = "Update activity using existing values")
	void partialUpdate(@PathVariable Long activityId, @RequestBody ActivityUpdateDTO activityUpdateDTO);

	@PutMapping("/{activityId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404",
			description = "Activity not found",
			content = @Content(
					schema = @Schema(
							implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200",
			description = "Updated",
			content = @Content(
					schema = @Schema(
							implementation = void.class)))
	@Operation(description = "Fully update activity")
	void update(@PathVariable Long activityId, @RequestBody ActivityCreateDTO activityUpdateDTO);

	@PageableAsQueryParam
	@GetMapping("/{taskId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "200",
			content = @Content(
					schema = @Schema(
							implementation = ActivityPage.class)))
	@Operation(description = "returns page of activities in task")
	DomainPage<ActivityResponseDTO> getPage(@PathVariable Long taskId, Pageable pageable);

}
