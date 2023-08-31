package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.EntityTask;
import com.wolfhack.todo.model.Task;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntityTaskMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTask toEntity(Task task);

	Task toModel(EntityTask entityTask);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTask partialUpdate(Task task, @MappingTarget EntityTask entityTask);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Task partialUpdate(Task from, @MappingTarget Task to);
}