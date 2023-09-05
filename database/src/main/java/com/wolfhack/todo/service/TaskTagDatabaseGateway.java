package com.wolfhack.todo.service;

import com.wolfhack.todo.adapter.database.TaskTagDatabaseAdapter;
import com.wolfhack.todo.annotation.DatabaseOperation;
import com.wolfhack.todo.exception.EntityNotFound;
import com.wolfhack.todo.mapper.EntityTaskTagMapper;
import com.wolfhack.todo.model.EntityTaskTag;
import com.wolfhack.todo.model.Tag;
import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.repository.TaskTagRepository;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.TaskTag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskTagDatabaseGateway implements TaskTagDatabaseAdapter {

	private final TaskTagRepository taskTagRepository;
	private final EntityTaskTagMapper taskTagMapper;

	@Override
	@DatabaseOperation
	public Long save(TaskTag model) {
		EntityTaskTag entity = taskTagMapper.toEntity(model);
		return taskTagRepository.save(entity).getId();
	}

	@Override
	@DatabaseOperation
	public Long partialUpdate(Long id, TaskTag model) {
		TaskTag taskTag = getById(id);
		taskTag = taskTagMapper.partialUpdate(model, taskTag);

		return save(taskTag);
	}

	@Override
	@DatabaseOperation
	public TaskTag getById(Long id) {
		return taskTagRepository.findById(id).map(taskTagMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	@DatabaseOperation
	public boolean exists(Long id) {
		return taskTagRepository.existsById(id);
	}

	@Override
	@DatabaseOperation
	public Collection<TaskTag> getById(Collection<Long> ids) {
		return taskTagRepository.findAllById(ids).stream().map(taskTagMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	public List<TaskTag> getAll() {
		return taskTagRepository.findAll().stream().map(taskTagMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	public DomainPage<TaskTag> getPage(Pageable pageable) {
		Page<TaskTag> page = taskTagRepository.findAll(pageable).map(taskTagMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	@DatabaseOperation
	public void delete(Long id) {
		taskTagRepository.deleteById(id);
	}

	@Override
	@DatabaseOperation
	public void deleteByTagId(Long tagId) {
		taskTagRepository.deleteByTag_Id(tagId);
	}

	@Override
	@DatabaseOperation
	public DomainPage<Task> getByTag(Long tagId, Pageable pageable) {
		Page<Task> page = taskTagRepository.findByTag_Id(tagId, pageable)
				.map(taskTagMapper::toModel)
				.map(TaskTag::getTask);

		return new DomainPage<>(page);
	}

	@Override
	@DatabaseOperation
	public DomainPage<Tag> getByTask(Collection<Long> taskIds, Pageable pageable) {
		Page<Tag> page = taskTagRepository.findAllByTask_IdIn(taskIds, pageable)
				.map(taskTagMapper::toModel)
				.map(TaskTag::getTag);

		return new DomainPage<>(page);
	}
}
