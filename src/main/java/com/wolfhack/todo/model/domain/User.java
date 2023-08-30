package com.wolfhack.todo.model.domain;

import com.wolfhack.todo.model.enums.Role;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User {

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

}
