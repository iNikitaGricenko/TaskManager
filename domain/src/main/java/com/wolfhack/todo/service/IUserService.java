package com.wolfhack.todo.service;

import com.wolfhack.todo.model.DomainPage;
import com.wolfhack.todo.model.domain.User;
import org.springframework.data.domain.Pageable;

public interface IUserService {

	Long save(User user);

	void update(Long id, User user);

	User getById(Long id);

	DomainPage<User> getPage(Pageable pageable);

}
