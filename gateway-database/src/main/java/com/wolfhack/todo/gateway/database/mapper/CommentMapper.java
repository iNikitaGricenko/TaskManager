package com.wolfhack.todo.gateway.database.mapper;

import com.wolfhack.todo.gateway.database.model.EntityComment;
import com.wolfhack.todo.model.domain.Comment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityComment toEntity(Comment comment);

	Comment toModel(EntityComment entityComment);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	EntityComment partialUpdate(Comment comment, @MappingTarget EntityComment entityComment);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Comment partialUpdate(Comment from, @MappingTarget Comment to);
}