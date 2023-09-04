package com.wolfhack.todo.service;

import com.wolfhack.todo.adapter.database.TagDatabaseAdapter;
import com.wolfhack.todo.error.EntityNotFound;
import com.wolfhack.todo.mapper.EntityTagMapper;
import com.wolfhack.todo.model.EntityTag;
import com.wolfhack.todo.repository.TagRepository;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TagDatabaseGateway implements TagDatabaseAdapter {

	private final TagRepository tagRepository;
	private final EntityTagMapper tagMapper;

	@Override
	public Long save(Tag model) {
		EntityTag entity = tagMapper.toEntity(model);
		return tagRepository.save(entity).getId();
	}

	@Override
	public Long partialUpdate(Long id, Tag model) {
		Tag tag = getById(id);
		tag = tagMapper.partialUpdate(model, tag);

		return save(tag);
	}

	@Override
	public Tag getById(Long id) {
		return tagRepository.findById(id).map(tagMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	public boolean exists(Long id) {
		return tagRepository.existsById(id);
	}

	@Override
	public Collection<Tag> getById(Collection<Long> ids) {
		return tagRepository.findAllById(ids).stream().map(tagMapper::toModel).toList();
	}

	@Override
	public List<Tag> getAll() {
		return tagRepository.findAll().stream().map(tagMapper::toModel).toList();
	}

	@Override
	public DomainPage<Tag> getPage(Pageable pageable) {
		Page<Tag> page = tagRepository.findAll(pageable).map(tagMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	public void delete(Long id) {
		tagRepository.deleteById(id);
	}

}
