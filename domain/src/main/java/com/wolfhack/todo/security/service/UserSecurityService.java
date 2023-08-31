package com.wolfhack.todo.security.service;

import com.wolfhack.todo.adapter.database.UserDatabaseAdapter;
import com.wolfhack.todo.security.model.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

	private final UserDatabaseAdapter userDatabaseAdapter;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.of(userDatabaseAdapter.getByUsername(username)).map(UserSecurity::new).get();
	}

}
