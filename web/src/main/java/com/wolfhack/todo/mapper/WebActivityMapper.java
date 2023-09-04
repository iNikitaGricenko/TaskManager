package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.model.create.ActivityCreateDTO;
import com.wolfhack.todo.model.response.ActivityResponseDTO;
import com.wolfhack.todo.model.update.ActivityUpdateDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebActivityMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Activity toModel(ActivityCreateDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Activity toModel(ActivityUpdateDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.id", target = "createdBy")
	@Mapping(source = "status.title", target = "status")
	ActivityResponseDTO toResponse(Activity model);

}