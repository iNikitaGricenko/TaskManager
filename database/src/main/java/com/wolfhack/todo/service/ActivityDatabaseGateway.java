package com.wolfhack.todo.service;

import com.wolfhack.todo.adapter.database.ActivityDatabaseAdapter;
import com.wolfhack.todo.error.EntityNotFound;
import com.wolfhack.todo.mapper.EntityActivityMapper;
import com.wolfhack.todo.model.EntityActivity;
import com.wolfhack.todo.repository.ActivityRepository;
import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Activity;
import lombok.RequiredArgsConstructor;
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
	public Long save(Activity model) {
		EntityActivity entity = activityMapper.toEntity(model);
		return activityRepository.save(entity).getId();
	}

	@Override
	public Long partialUpdate(Long id, Activity model) {
		Activity activity = getById(id);
		activity = activityMapper.partialUpdate(model, activity);

		return save(activity);
	}

	@Override
	public Long update(Long id, Activity model) {
		Activity activity = getById(id);
		activity = activityMapper.update(model, activity);

		return save(activity);
	}

	@Override
	public Activity getById(Long id) {
		return activityRepository.findById(id).map(activityMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	public boolean exists(Long id) {
		return activityRepository.existsById(id);
	}

	@Override
	public Collection<Activity> getById(Collection<Long> ids) {
		return activityRepository.findAllById(ids).stream().map(activityMapper::toModel).toList();
	}

	@Override
	public List<Activity> getAll() {
		return activityRepository.findAll().stream().map(activityMapper::toModel).toList();
	}

	@Override
	public DomainPage<Activity> getPage(Pageable pageable) {
		Page<Activity> page = activityRepository.findAll(pageable).map(activityMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	public void delete(Long id) {
		activityRepository.deleteById(id);
	}

	@Override
	public DomainPage<Activity> getByTask(Long taskId, Pageable pageable) {
		Page<Activity> page = activityRepository.findByTask_Id(taskId, pageable).map(activityMapper::toModel);
		return new DomainPage<>(page);
	}
}
