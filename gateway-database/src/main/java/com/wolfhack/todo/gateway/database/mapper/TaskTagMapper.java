package com.wolfhack.todo.gateway.database.mapper;

import com.wolfhack.todo.gateway.database.model.EntityTaskTag;
import com.wolfhack.todo.model.domain.TaskTag;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskTagMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTaskTag toEntity(TaskTag taskTag);

	TaskTag toModel(EntityTaskTag entityTaskTag);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTaskTag partialUpdate(TaskTag taskTag, @MappingTarget EntityTaskTag entityTaskTag);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	TaskTag partialUpdate(TaskTag from, @MappingTarget TaskTag to);
}