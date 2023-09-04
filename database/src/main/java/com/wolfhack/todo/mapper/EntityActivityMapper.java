package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.EntityActivity;
import com.wolfhack.todo.model.Activity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
		uses = {EntityTaskMapper.class, EntityUserMapper.class})
public interface EntityActivityMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "taskId", target = "task.id")
	EntityActivity toEntity(Activity activity);

	@Mapping(source = "task.id", target = "taskId")
	Activity toModel(EntityActivity entityActivity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityActivity partialUpdate(Activity activity, @MappingTarget EntityActivity entityActivity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Activity partialUpdate(Activity from, @MappingTarget Activity to);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
	Activity update(Activity from, @MappingTarget Activity to);
}