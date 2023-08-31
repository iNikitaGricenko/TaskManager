package com.wolfhack.todo.gateway.database.service;

import com.wolfhack.todo.adapter.database.TagDatabaseAdapter;
import com.wolfhack.todo.gateway.database.error.EntityNotFound;
import com.wolfhack.todo.gateway.database.mapper.TagMapper;
import com.wolfhack.todo.gateway.database.model.EntityTag;
import com.wolfhack.todo.gateway.database.repository.TagRepository;
import com.wolfhack.todo.model.DomainPage;
import com.wolfhack.todo.model.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class TagDatabaseGateway implements TagDatabaseAdapter {

	private final TagRepository tagRepository;
	private final TagMapper tagMapper;

	@Override
	public Long save(Tag model) {
		EntityTag entity = tagMapper.toEntity(model);
		return tagRepository.save(entity).getId();
	}

	@Override
	public Long update(Long id, Tag model) {
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
