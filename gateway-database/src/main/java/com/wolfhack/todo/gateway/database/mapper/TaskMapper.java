package com.wolfhack.todo.gateway.database.mapper;

import com.wolfhack.todo.gateway.database.model.EntityTask;
import com.wolfhack.todo.model.domain.Task;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTask toEntity(Task task);

	Task toModel(EntityTask entityTask);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTask partialUpdate(Task task, @MappingTarget EntityTask entityTask);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Task partialUpdate(Task from, @MappingTarget Task to);
}