package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.model.create.TaskCreateDTO;
import com.wolfhack.todo.model.response.TaskResponseDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebTaskMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "userId", target = "user.id")
	@Mapping(source = "userUsername", target = "user.username")
	@Mapping(source = "userEmail", target = "user.email")
	Task toModel(TaskCreateDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.id", target = "createdBy")
	@Mapping(source = "status.title", target = "status")
	TaskResponseDTO toResponse(Task model);
}