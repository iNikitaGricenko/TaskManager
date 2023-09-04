package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.CreateTaskMetaDTO;
import com.wolfhack.todo.model.TaskMeta;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebTaskMetaMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	TaskMeta toModel(CreateTaskMetaDTO createTaskMetaDTO);

}