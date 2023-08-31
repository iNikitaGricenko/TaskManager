package com.wolfhack.todo.security.model;

import com.wolfhack.todo.model.User;
import com.wolfhack.todo.model.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class UserSecurity implements UserDetails {

	private Long id;
	private Collection<? extends GrantedAuthority> authorities;
	private String password;
	private String username;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	public UserSecurity(User user) {
		this.id = user.getId();
		this.authorities = Collections.singleton(user.getRole());
		this.password = user.getPassword();
		this.username = user.getEmail();
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
	}

	public UserSecurity(Long id, Role authority, String password, String username, boolean enabled) {
		this.id = id;
		this.authorities = Collections.singleton(authority);
		this.password = password;
		this.username = username;
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = enabled;
	}

}
