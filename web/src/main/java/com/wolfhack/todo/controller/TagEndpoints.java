package com.wolfhack.todo.controller;

import com.wolfhack.todo.exception.error.ValidationErrorBody;
import com.wolfhack.todo.model.create.TagCreateDTO;
import com.wolfhack.todo.model.response.TaskResponseDTO;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.wrapper.swagger.TaskPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tag endpoints")
public interface TagEndpoints {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "400", description = "Request body is invalid", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
	@ApiResponse(responseCode = "201", description = "Created and returned id", content = @Content(schema = @Schema(implementation = long.class)))
	@Operation(description = "Create new tag")
	long create(@RequestBody TagCreateDTO tagCreateDTO);

	@PageableAsQueryParam
	@GetMapping("/task/{tagId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "200",
			content = @Content(
					schema = @Schema(
							implementation = TaskPage.class)))
	@Operation(description = "Retrieves all tasks that contains this tag. Use path to specify tag")
	DomainPage<TaskResponseDTO> getTasksByTag(@PathVariable Long tagId, Pageable pageable);

}
