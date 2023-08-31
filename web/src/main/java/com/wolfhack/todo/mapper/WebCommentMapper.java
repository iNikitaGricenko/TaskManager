package com.wolfhack.todo.mapper;

import com.wolfhack.todo.model.Comment;
import com.wolfhack.todo.model.create.CommentCreateDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebCommentMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Comment toModel(CommentCreateDTO dto);

}