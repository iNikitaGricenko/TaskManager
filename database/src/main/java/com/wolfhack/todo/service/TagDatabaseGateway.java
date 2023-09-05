package com.wolfhack.todo.service;

import com.wolfhack.todo.adapter.database.TagDatabaseAdapter;
import com.wolfhack.todo.annotation.DatabaseOperation;
import com.wolfhack.todo.exception.EntityNotFound;
import com.wolfhack.todo.mapper.EntityTagMapper;
import com.wolfhack.todo.model.EntityTag;
import com.wolfhack.todo.repository.TagRepository;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
	@CachePut(cacheNames = {"tag", "tag_page"}, key = "#model.id")
	public Long save(Tag model) {
		EntityTag entity = tagMapper.toEntity(model);
		return tagRepository.save(entity).getId();
	}

	@Override
	@CachePut(cacheNames = {"tag", "tag_page"}, key = "#model.id")
	public Long partialUpdate(Long id, Tag model) {
		Tag tag = getById(id);
		tag = tagMapper.partialUpdate(model, tag);

		return save(tag);
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"tag"}, key = "#id")
	public Tag getById(Long id) {
		return tagRepository.findById(id).map(tagMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"tag"}, key = "#id")
	public boolean exists(Long id) {
		return tagRepository.existsById(id);
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"tag"}, key = "#ids")
	public Collection<Tag> getById(Collection<Long> ids) {
		return tagRepository.findAllById(ids).stream().map(tagMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"tag"})
	public List<Tag> getAll() {
		return tagRepository.findAll().stream().map(tagMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"tag_page"})
	public DomainPage<Tag> getPage(Pageable pageable) {
		Page<Tag> page = tagRepository.findAll(pageable).map(tagMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	@DatabaseOperation
	@CacheEvict(cacheNames = {"tag", "tag_page"}, key = "#id")
	public void delete(Long id) {
		tagRepository.deleteById(id);
	}

}
