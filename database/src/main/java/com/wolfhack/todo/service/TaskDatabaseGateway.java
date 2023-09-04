package com.wolfhack.todo.service;

import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.error.EntityNotFound;
import com.wolfhack.todo.mapper.EntityTaskMapper;
import com.wolfhack.todo.model.EntityTask;
import com.wolfhack.todo.repository.TaskRepository;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskDatabaseGateway implements TaskDatabaseAdapter {

	private final TaskRepository taskRepository;
	private final EntityTaskMapper taskMapper;

	@Override
	public Long save(Task model) {
		EntityTask entity = taskMapper.toEntity(model);
		return taskRepository.save(entity).getId();
	}

	@Override
	public Long partialUpdate(Long id, Task model) {
		Task task = getById(id);
		task = taskMapper.partialUpdate(model, task);

		return save(task);
	}

	@Override
	public Task getById(Long id) {
		return taskRepository.findById(id).map(taskMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	public boolean exists(Long id) {
		return taskRepository.existsById(id);
	}

	@Override
	public Collection<Task> getById(Collection<Long> ids) {
		return taskRepository.findAllById(ids).stream().map(taskMapper::toModel).toList();
	}

	@Override
	public List<Task> getAll() {
		return taskRepository.findAll().stream().map(taskMapper::toModel).toList();
	}

	@Override
	public DomainPage<Task> getPage(Pageable pageable) {
		Page<Task> page = taskRepository.findAll(pageable).map(taskMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	public void delete(Long id) {
		taskRepository.deleteById(id);
	}

	@Override
	public long update(long id, Task model) {
		Task task = getById(id);
		task = taskMapper.update(model, task);

		return save(task);
	}
}
