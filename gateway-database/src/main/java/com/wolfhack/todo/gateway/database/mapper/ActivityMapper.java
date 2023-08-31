package com.wolfhack.todo.gateway.database.mapper;

import com.wolfhack.todo.gateway.database.model.EntityActivity;
import com.wolfhack.todo.model.domain.Activity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActivityMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityActivity toEntity(Activity activity);

	Activity toModel(EntityActivity entityActivity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityActivity partialUpdate(Activity activity, @MappingTarget EntityActivity entityActivity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Activity partialUpdate(Activity from, @MappingTarget Activity to);
}