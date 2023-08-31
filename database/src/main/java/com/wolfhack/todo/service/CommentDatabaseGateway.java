package com.wolfhack.todo.service;

import com.wolfhack.todo.adapter.database.CommentDatabaseAdapter;
import com.wolfhack.todo.error.EntityNotFound;
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
	public Long save(Comment model) {
		EntityComment entity = commentMapper.toEntity(model);
		return commentRepository.save(entity).getId();
	}

	@Override
	public Long update(Long id, Comment model) {
		Comment comment = getById(id);
		comment = commentMapper.partialUpdate(model, comment);

		return save(comment);
	}

	@Override
	public Comment getById(Long id) {
		return commentRepository.findById(id).map(commentMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	public boolean exists(Long id) {
		return commentRepository.existsById(id);
	}

	@Override
	public Collection<Comment> getById(Collection<Long> ids) {
		return commentRepository.findAllById(ids).stream().map(commentMapper::toModel).toList();
	}

	@Override
	public List<Comment> getAll() {
		return commentRepository.findAll().stream().map(commentMapper::toModel).toList();
	}

	@Override
	public DomainPage<Comment> getPage(Pageable pageable) {
		Page<Comment> page = commentRepository.findAll(pageable).map(commentMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	public void delete(Long id) {
		commentRepository.deleteById(id);
	}

	@Override
	public DomainPage<Comment> getByTask(Long id, Pageable pageable) {
		Page<Comment> page = commentRepository.findByTask_Id(id, pageable).map(commentMapper::toModel);
		return new DomainPage<>(page);
	}
}