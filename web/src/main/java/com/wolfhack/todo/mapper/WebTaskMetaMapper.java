package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.create.TaskMetaCreateDTO;
import com.wolfhack.todo.model.TaskMeta;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebTaskMetaMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	TaskMeta toModel(TaskMetaCreateDTO createTaskMetaDTO);

}