package com.wolfhack.todo.gateway.database.mapper;

import com.wolfhack.todo.gateway.database.model.EntityTag;
import com.wolfhack.todo.model.domain.Tag;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTag toEntity(Tag tag);

	Tag toModel(EntityTag entityTag);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityTag partialUpdate(Tag tag, @MappingTarget EntityTag entityTag);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Tag partialUpdate(Tag from, @MappingTarget Tag to);
}