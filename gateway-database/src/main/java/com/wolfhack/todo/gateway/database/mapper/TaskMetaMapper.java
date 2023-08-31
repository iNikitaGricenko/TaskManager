package com.wolfhack.todo.gateway.database.mapper;

import com.wolfhack.todo.gateway.database.model.EntityTaskMeta;
import com.wolfhack.todo.model.domain.TaskMeta;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMetaMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTaskMeta toEntity(TaskMeta taskMeta);

	TaskMeta toModel(EntityTaskMeta entityTaskMeta);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTaskMeta partialUpdate(TaskMeta taskMeta, @MappingTarget EntityTaskMeta entityTaskMeta);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	TaskMeta partialUpdate(TaskMeta from, @MappingTarget TaskMeta to);
}