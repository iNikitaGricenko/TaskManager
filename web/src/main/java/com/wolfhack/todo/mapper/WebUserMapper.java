package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.User;
import com.wolfhack.todo.model.create.UserCreateDTO;
import com.wolfhack.todo.model.create.UserLoginDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebUserMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	User toModel(UserCreateDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	User toModel(UserLoginDTO userLoginDTO);
}