package com.wolfhack.todo.gateway.database.service;

import com.wolfhack.todo.adapter.database.TaskTagDatabaseAdapter;
import com.wolfhack.todo.gateway.database.error.EntityNotFound;
import com.wolfhack.todo.gateway.database.mapper.TaskTagMapper;
import com.wolfhack.todo.gateway.database.model.EntityTaskTag;
import com.wolfhack.todo.gateway.database.repository.TaskTagRepository;
import com.wolfhack.todo.model.DomainPage;
import com.wolfhack.todo.model.domain.TaskTag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class TaskTagDatabaseGateway implements TaskTagDatabaseAdapter {

	private final TaskTagRepository taskTagRepository;
	private final TaskTagMapper taskTagMapper;

	@Override
	public Long save(TaskTag model) {
		EntityTaskTag entity = taskTagMapper.toEntity(model);
		return taskTagRepository.save(entity).getId();
	}

	@Override
	public Long update(Long id, TaskTag model) {
		TaskTag taskTag = getById(id);
		taskTag = taskTagMapper.partialUpdate(model, taskTag);

		return save(taskTag);
	}

	@Override
	public TaskTag getById(Long id) {
		return taskTagRepository.findById(id).map(taskTagMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	public boolean exists(Long id) {
		return taskTagRepository.existsById(id);
	}

	@Override
	public Collection<TaskTag> getById(Collection<Long> ids) {
		return taskTagRepository.findAllById(ids).stream().map(taskTagMapper::toModel).toList();
	}

	@Override
	public List<TaskTag> getAll() {
		return taskTagRepository.findAll().stream().map(taskTagMapper::toModel).toList();
	}

	@Override
	public DomainPage<TaskTag> getPage(Pageable pageable) {
		Page<TaskTag> page = taskTagRepository.findAll(pageable).map(taskTagMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	public void delete(Long id) {
		taskTagRepository.deleteById(id);
	}

	@Override
	public void deleteByTagId(Long tagId) {
		taskTagRepository.deleteByTag_Id(tagId);
	}
}
