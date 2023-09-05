package com.wolfhack.todo.service;

import com.wolfhack.todo.adapter.database.ActivityDatabaseAdapter;
import com.wolfhack.todo.annotation.DatabaseOperation;
import com.wolfhack.todo.exception.EntityNotFound;
import com.wolfhack.todo.mapper.EntityActivityMapper;
import com.wolfhack.todo.model.EntityActivity;
import com.wolfhack.todo.repository.ActivityRepository;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Activity;
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
public class ActivityDatabaseGateway implements ActivityDatabaseAdapter {

	private final ActivityRepository activityRepository;
	private final EntityActivityMapper activityMapper;

	@Override
	@DatabaseOperation
	@CachePut(cacheNames = {"activity", "activity_page"}, key = "#model.id")
	public Long save(Activity model) {
		EntityActivity entity = activityMapper.toEntity(model);
		return activityRepository.save(entity).getId();
	}

	@Override
	@DatabaseOperation
	@CachePut(cacheNames = {"activity", "activity_page"}, key = "#id")
	public Long partialUpdate(Long id, Activity model) {
		Activity activity = getById(id);
		activity = activityMapper.partialUpdate(model, activity);

		return save(activity);
	}

	@Override
	@DatabaseOperation
	@CachePut(cacheNames = {"activity", "activity_page"}, key = "#id")
	public Long update(Long id, Activity model) {
		Activity activity = getById(id);
		activity = activityMapper.update(model, activity);

		return save(activity);
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"activity"}, key = "#id")
	public Activity getById(Long id) {
		return activityRepository.findById(id).map(activityMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"activity"}, key = "#id")
	public boolean exists(Long id) {
		return activityRepository.existsById(id);
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"activity"}, key = "#ids")
	public Collection<Activity> getById(Collection<Long> ids) {
		return activityRepository.findAllById(ids).stream().map(activityMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"activity"})
	public List<Activity> getAll() {
		return activityRepository.findAll().stream().map(activityMapper::toModel).toList();
	}

	@Override
	@DatabaseOperation
	@Cacheable(cacheNames = {"activity_page"})
	public DomainPage<Activity> getPage(Pageable pageable) {
		Page<Activity> page = activityRepository.findAll(pageable).map(activityMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	@DatabaseOperation
	@CacheEvict(cacheNames = {"activity", "activity_page"}, key = "#id")
	public void delete(Long id) {
		activityRepository.deleteById(id);
	}

	@Override
	@DatabaseOperation
	public DomainPage<Activity> getByTask(Long taskId, Pageable pageable) {
		Page<Activity> page = activityRepository.findByTask_Id(taskId, pageable).map(activityMapper::toModel);
		return new DomainPage<>(page);
	}
}
