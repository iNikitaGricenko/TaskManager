package com.wolfhack.todo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

	NONE("ROLE_USER"),
	JUNIOR("ROLE_USER"),
	MIDDLE("ROLE_USER"),
	SENIOR("ROLE_USER"),
	TEAM_LEAD("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String authority;

}
