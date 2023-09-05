package com.wolfhack.todo.service;

import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.annotation.DatabaseOperation;
import com.wolfhack.todo.exception.EntityNotFound;
import com.wolfhack.todo.mapper.EntityTaskMapper;
import com.wolfhack.todo.model.EntityTask;
import com.wolfhack.todo.repository.TaskRepository;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskDatabaseGateway implements TaskDatabaseAdapter {

	private final TaskRepository taskRepository;
	private final EntityTaskMapper taskMapper;

	@Override
	@DatabaseOperation
	@CachePut(cacheNames = {"task", "task_page"}, key = "#model.id")
	public Long save(Task model) {
		EntityTask entity = taskMapper.toEntity(model);
		return taskRepository.save(entity).getId();
	}

	@Override
	@DatabaseOperation
	@CachePut(cacheNames = {"task", "task_page"}, key = "#id")
	public Long partialUpdate(Long id, Task model) {
		Task task = getById(id);
		task = taskMapper.partialUpdate(model, task);

		return save(task);
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"task"}, key = "#id")
	public Task getById(Long id) {
		return taskRepository.findById(id).map(taskMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"task"}, key = "#id")
	public boolean exists(Long id) {
		return taskRepository.existsById(id);
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"task"}, key = "#ids")
	public Collection<Task> getById(Collection<Long> ids) {
		return taskRepository.findAllById(ids).stream().map(taskMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"task"})
	public List<Task> getAll() {
		return taskRepository.findAll().stream().map(taskMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"task_page"})
	public DomainPage<Task> getPage(Pageable pageable) {
		Page<Task> page = taskRepository.findAll(pageable).map(taskMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	@DatabaseOperation
	@CacheEvict(cacheNames = {"task", "task_page"}, key = "#id")
	public void delete(Long id) {
		taskRepository.deleteById(id);
	}

	@Override
	@DatabaseOperation
	@CachePut(cacheNames = {"task", "task_page"}, key = "#id")
	public long update(long id, Task model) {
		Task task = getById(id);
		task = taskMapper.update(model, task);

		return save(task);
	}

	@Override
	public List<Task> getAllPlannedToStart() {
		LocalDateTime startDay = LocalDate.now().atStartOfDay();
		LocalDateTime endDay = LocalDate.now().atTime(LocalTime.MIN);
		return taskRepository.findAllByPlannedStartDateIsBetweenAndActualStartDateIsNull(startDay, endDay).stream()
				.map(taskMapper::toModel).toList();
	}

	@Override
	public List<Task> getAllPlannedToEnd() {
		LocalDateTime startDay = LocalDate.now().atStartOfDay();
		LocalDateTime endDay = LocalDate.now().atTime(LocalTime.MIN);
		return taskRepository.findAllByPlannedEndDateIsBetweenAndActualEndDateIsNull(startDay, endDay).stream()
				.map(taskMapper::toModel).toList();
	}
}
