package com.wolfhack.todo.service;

import com.wolfhack.todo.adapter.database.TaskMetaDatabaseAdapter;
import com.wolfhack.todo.annotation.DatabaseOperation;
import com.wolfhack.todo.exception.EntityNotFound;
import com.wolfhack.todo.mapper.EntityTaskMetaMapper;
import com.wolfhack.todo.model.EntityTaskMeta;
import com.wolfhack.todo.repository.TaskMetaRepository;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.TaskMeta;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMetaDatabaseGateway implements TaskMetaDatabaseAdapter {

	private final TaskMetaRepository taskMetaRepository;
	private final EntityTaskMetaMapper taskMetaMapper;

	@Override
	@DatabaseOperation
	public Long save(TaskMeta model) {
		EntityTaskMeta entity = taskMetaMapper.toEntity(model);
		return taskMetaRepository.save(entity).getId();
	}

	@Override
	@DatabaseOperation
	public Long partialUpdate(Long id, TaskMeta model) {
		TaskMeta taskMeta = getById(id);
		taskMeta = taskMetaMapper.partialUpdate(model, taskMeta);

		return save(taskMeta);
	}

	@Override
	@DatabaseOperation
	public TaskMeta getById(Long id) {
		return taskMetaRepository.findById(id).map(taskMetaMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	@DatabaseOperation
	public boolean exists(Long id) {
		return taskMetaRepository.existsById(id);
	}

	@Override
	@DatabaseOperation
	public Collection<TaskMeta> getById(Collection<Long> ids) {
		return taskMetaRepository.findAllById(ids).stream().map(taskMetaMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	public List<TaskMeta> getAll() {
		return taskMetaRepository.findAll().stream().map(taskMetaMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	public DomainPage<TaskMeta> getPage(Pageable pageable) {
		Page<TaskMeta> page = taskMetaRepository.findAll(pageable).map(taskMetaMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	@DatabaseOperation
	public void delete(Long id) {
		taskMetaRepository.deleteById(id);
	}

}
