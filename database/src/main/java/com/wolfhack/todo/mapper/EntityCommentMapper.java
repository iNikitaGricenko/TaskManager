package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.EntityComment;
import com.wolfhack.todo.model.Comment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
		uses = {EntityTaskMapper.class, EntityUserMapper.class})
public interface EntityCommentMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityComment toEntity(Comment comment);

	Comment toModel(EntityComment entityComment);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityComment partialUpdate(Comment comment, @MappingTarget EntityComment entityComment);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "task", source = ".")
	Comment partialUpdate(Comment from, @MappingTarget Comment to);
}