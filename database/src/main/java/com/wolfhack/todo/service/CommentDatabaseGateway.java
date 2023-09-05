package com.wolfhack.todo.service;

import com.wolfhack.todo.adapter.database.CommentDatabaseAdapter;
import com.wolfhack.todo.annotation.DatabaseOperation;
import com.wolfhack.todo.exception.EntityNotFound;
import com.wolfhack.todo.mapper.EntityCommentMapper;
import com.wolfhack.todo.model.EntityComment;
import com.wolfhack.todo.repository.CommentRepository;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentDatabaseGateway implements CommentDatabaseAdapter {

	private final CommentRepository commentRepository;
	private final EntityCommentMapper commentMapper;

	@Override
	@DatabaseOperation
	public Long save(Comment model) {
		EntityComment entity = commentMapper.toEntity(model);
		return commentRepository.save(entity).getId();
	}

	@Override
	@DatabaseOperation
	public Long partialUpdate(Long id, Comment model) {
		Comment comment = getById(id);
		comment = commentMapper.partialUpdate(model, comment);

		return save(comment);
	}

	@Override
	@DatabaseOperation
	public Comment getById(Long id) {
		return commentRepository.findById(id).map(commentMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	@DatabaseOperation
	public boolean exists(Long id) {
		return commentRepository.existsById(id);
	}

	@Override
	@DatabaseOperation
	public Collection<Comment> getById(Collection<Long> ids) {
		return commentRepository.findAllById(ids).stream().map(commentMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	public List<Comment> getAll() {
		return commentRepository.findAll().stream().map(commentMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	public DomainPage<Comment> getPage(Pageable pageable) {
		Page<Comment> page = commentRepository.findAll(pageable).map(commentMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	@DatabaseOperation
	public void delete(Long id) {
		commentRepository.deleteById(id);
	}

	@Override
	@DatabaseOperation
	public DomainPage<Comment> getByTask(Long id, Pageable pageable) {
		Page<Comment> page = commentRepository.findByTask_Id(id, pageable).map(commentMapper::toModel);
		return new DomainPage<>(page);
	}
}
