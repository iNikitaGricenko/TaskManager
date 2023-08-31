package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.EntityUser;
import com.wolfhack.todo.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntityUserMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityUser toEntity(User user);

	User toModel(EntityUser entityUser);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityUser partialUpdate(User user, @MappingTarget EntityUser entityUser);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	User partialUpdate(User from, @MappingTarget User to);
}