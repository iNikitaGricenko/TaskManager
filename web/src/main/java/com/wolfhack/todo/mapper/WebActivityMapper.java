package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.model.create.ActivityCreateDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebActivityMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Activity toModel(ActivityCreateDTO dto);

}