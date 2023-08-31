package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.model.create.TaskCreateDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebTaskMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Task toModel(TaskCreateDTO dto);
}