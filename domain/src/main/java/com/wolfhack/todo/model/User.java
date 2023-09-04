package com.wolfhack.todo.model;

import com.wolfhack.todo.adapter.DomainModel;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class User implements DomainModel {

	private Long id;

	private Role role;

	private String firstName;

	private String lastName;

	private String username;

	private String email;

	private String password;

	private LocalDate registrationDate;

	private LocalDateTime lastLogin;

	private boolean online;

	private String intro;

	private String profile;

	public void register(PasswordEncoder encoder) {
		this.password = encoder.encode(password);
		this.registrationDate = LocalDate.now();
		this.role = Optional.ofNullable(this.role).orElse(Role.DEFAULT);

		login();
	}

	public void login() {
		this.online = true;
		this.lastLogin = LocalDateTime.now();
	}

}
