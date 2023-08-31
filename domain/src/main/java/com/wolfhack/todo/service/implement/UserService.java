package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.UserDatabaseAdapter;
import com.wolfhack.todo.model.DomainPage;
import com.wolfhack.todo.model.domain.User;
import com.wolfhack.todo.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

	private final UserDatabaseAdapter userDatabaseAdapter;
	private final PasswordEncoder passwordEncoder;

	public Long save(User user) {
		user.register(passwordEncoder);
		return userDatabaseAdapter.save(user);
	}

	@Override
	public void update(Long id, User user) {
		userDatabaseAdapter.update(id, user);
	}

	public User getById(Long id) {
		return userDatabaseAdapter.getById(id);
	}

	public DomainPage<User> getPage(Pageable pageable) {
		return userDatabaseAdapter.getPage(pageable);
	}

}