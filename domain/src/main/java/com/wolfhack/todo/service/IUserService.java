package com.wolfhack.todo.service;

import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.User;
import org.springframework.data.domain.Pageable;

public interface IUserService {

	Long save(User user);

	void update(Long id, User user);

	User getById(Long id);

	DomainPage<User> getPage(Pageable pageable);

	User getByUsername(String username);
}
