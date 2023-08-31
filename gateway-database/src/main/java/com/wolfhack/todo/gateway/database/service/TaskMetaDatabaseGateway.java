package com.wolfhack.todo.gateway.database.service;

import com.wolfhack.todo.adapter.database.TaskMetaDatabaseAdapter;
import com.wolfhack.todo.gateway.database.error.EntityNotFound;
import com.wolfhack.todo.gateway.database.mapper.TaskMetaMapper;
import com.wolfhack.todo.gateway.database.model.EntityTaskMeta;
import com.wolfhack.todo.gateway.database.repository.TaskMetaRepository;
import com.wolfhack.todo.model.DomainPage;
import com.wolfhack.todo.model.domain.TaskMeta;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class TaskMetaDatabaseGateway implements TaskMetaDatabaseAdapter {

	private final TaskMetaRepository taskMetaRepository;
	private final TaskMetaMapper taskMetaMapper;

	@Override
	public Long save(TaskMeta model) {
		EntityTaskMeta entity = taskMetaMapper.toEntity(model);
		return taskMetaRepository.save(entity).getId();
	}

	@Override
	public Long update(Long id, TaskMeta model) {
		TaskMeta taskMeta = getById(id);
		taskMeta = taskMetaMapper.partialUpdate(model, taskMeta);

		return save(taskMeta);
	}

	@Override
	public TaskMeta getById(Long id) {
		return taskMetaRepository.findById(id).map(taskMetaMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	public boolean exists(Long id) {
		return taskMetaRepository.existsById(id);
	}

	@Override
	public Collection<TaskMeta> getById(Collection<Long> ids) {
		return taskMetaRepository.findAllById(ids).stream().map(taskMetaMapper::toModel).toList();
	}

	@Override
	public List<TaskMeta> getAll() {
		return taskMetaRepository.findAll().stream().map(taskMetaMapper::toModel).toList();
	}

	@Override
	public DomainPage<TaskMeta> getPage(Pageable pageable) {
		Page<TaskMeta> page = taskMetaRepository.findAll(pageable).map(taskMetaMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	public void delete(Long id) {
		taskMetaRepository.deleteById(id);
	}

}
