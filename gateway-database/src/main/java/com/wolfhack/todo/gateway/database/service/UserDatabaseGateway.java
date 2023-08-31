package com.wolfhack.todo.gateway.database.service;

import com.wolfhack.todo.adapter.database.UserDatabaseAdapter;
import com.wolfhack.todo.gateway.database.error.EntityNotFound;
import com.wolfhack.todo.gateway.database.mapper.UserMapper;
import com.wolfhack.todo.gateway.database.model.EntityUser;
import com.wolfhack.todo.gateway.database.repository.UserRepository;
import com.wolfhack.todo.model.DomainPage;
import com.wolfhack.todo.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserDatabaseGateway implements UserDatabaseAdapter {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public Long save(User model) {
		EntityUser entity = userMapper.toEntity(model);
		return userRepository.save(entity).getId();
	}

	@Override
	public Long update(Long id, User model) {
		User user = getById(id);
		user = userMapper.partialUpdate(model, user);

		return save(user);
	}

	@Override
	public User getById(Long id) {
		return userRepository.findById(id).map(userMapper::toModel).orElseThrow(EntityNotFound::new);
	}

	@Override
	public Collection<User> getById(Collection<Long> ids) {
		return userRepository.findAllById(ids).stream().map(userMapper::toModel).toList();
	}

	@Override
	public boolean exists(Long id) {
		return userRepository.existsById(id);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll().stream().map(userMapper::toModel).toList();
	}

	@Override
	public DomainPage<User> getPage(Pageable pageable) {
		Page<User> page = userRepository.findAll(pageable).map(userMapper::toModel);
		return new DomainPage<>(page);
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getByUsername(String username) {
		return userRepository.findByUsername(username).map(userMapper::toModel).orElseThrow();
	}

	@Override
	public User getByEmail(String email) {
		return userRepository.findByEmail(email).map(userMapper::toModel).orElseThrow();
	}
}