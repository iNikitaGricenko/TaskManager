package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.EntityTaskMeta;
import com.wolfhack.todo.model.TaskMeta;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntityTaskMetaMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTaskMeta toEntity(TaskMeta taskMeta);

	TaskMeta toModel(EntityTaskMeta entityTaskMeta);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTaskMeta partialUpdate(TaskMeta taskMeta, @MappingTarget EntityTaskMeta entityTaskMeta);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	TaskMeta partialUpdate(TaskMeta from, @MappingTarget TaskMeta to);
}