package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.EntityActivity;
import com.wolfhack.todo.model.Activity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntityActivityMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityActivity toEntity(Activity activity);

	Activity toModel(EntityActivity entityActivity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityActivity partialUpdate(Activity activity, @MappingTarget EntityActivity entityActivity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Activity partialUpdate(Activity from, @MappingTarget Activity to);
}