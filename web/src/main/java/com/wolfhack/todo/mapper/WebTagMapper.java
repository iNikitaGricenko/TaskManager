package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.Tag;
import com.wolfhack.todo.model.create.TagCreateDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebTagMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Tag toModel(TagCreateDTO dto);
}