package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.EntityTaskTag;
import com.wolfhack.todo.model.TaskTag;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntityTaskTagMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTaskTag toEntity(TaskTag taskTag);

	TaskTag toModel(EntityTaskTag entityTaskTag);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTaskTag partialUpdate(TaskTag taskTag, @MappingTarget EntityTaskTag entityTaskTag);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	TaskTag partialUpdate(TaskTag from, @MappingTarget TaskTag to);
}