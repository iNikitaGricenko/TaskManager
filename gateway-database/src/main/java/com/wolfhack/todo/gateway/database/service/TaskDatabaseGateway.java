package com.wolfhack.todo.gateway.database.service;

import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.gateway.database.error.EntityNotFound;
import com.wolfhack.todo.gateway.database.mapper.TaskMapper;
import com.wolfhack.todo.gateway.database.model.EntityTask;
import com.wolfhack.todo.gateway.database.repository.TaskRepository;
import com.wolfhack.todo.model.DomainPage;
import com.wolfhack.todo.model.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class TaskDatabaseGateway implements TaskDatabaseAdapter {

	private final TaskRepository taskRepository;
	private final TaskMapper taskMapper;

	@Override
	public Long save(Task model) {
		EntityTask entity = taskMapper.toEntity(model);
		return taskRepository.save(entity).getId();
	}

	@Override
	public Long update(Long id, Task model) {
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

}
