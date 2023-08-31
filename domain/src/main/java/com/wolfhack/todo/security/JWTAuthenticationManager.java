package com.wolfhack.todo.security;

import com.wolfhack.todo.security.model.UserSecurity;
import com.wolfhack.todo.security.service.JwtSigner;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationManager implements AuthenticationManager {

	private final JwtSigner jwtSigner;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String jwtToken = authentication.getCredentials().toString();
		return Optional.of(jwtSigner.validateAndReturnInfo(jwtToken)).map(this::getAuthorities).get();
	}

	private UsernamePasswordAuthenticationToken getAuthorities(UserSecurity userAuthority) {
		return new UsernamePasswordAuthenticationToken(userAuthority, userAuthority.getUsername(), userAuthority.getAuthorities());
	}

}
