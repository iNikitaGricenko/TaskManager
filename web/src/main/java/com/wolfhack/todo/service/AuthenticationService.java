package com.wolfhack.todo.service;

import com.wolfhack.todo.model.JwtResponse;
import com.wolfhack.todo.model.User;
import com.wolfhack.todo.security.model.UserSecurity;
import com.wolfhack.todo.security.service.JwtSigner;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

	private final IUserService userService;
	private final JwtSigner jwtSigner;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	@Override
	public JwtResponse signUp(User user) {
		Long id = userService.save(user);
		user = userService.getById(id);
		String jwt = jwtSigner.create(user.getUsername());
		return new JwtResponse(jwt);
	}

	@Override
	public JwtResponse signIn(User user) {
		User registeredUser = userService.getByUsername(user.getUsername());

		if (!passwordEncoder.matches(user.getPassword(), registeredUser.getPassword())) {
			throw new AuthenticationCredentialsNotFoundException("Username or password are not valid");
		}

		String jwt = jwtSigner.create(user.getUsername());

		UserSecurity userSecurity = new UserSecurity(registeredUser);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwt, jwt, null);
		authenticationManager.authenticate(authentication);

		return new JwtResponse(jwt);
	}
}
