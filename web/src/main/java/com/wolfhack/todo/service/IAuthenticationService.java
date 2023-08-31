package com.wolfhack.todo.service;

import com.wolfhack.todo.model.JwtResponse;
import com.wolfhack.todo.model.User;

public interface IAuthenticationService {

	JwtResponse signUp(User user);

	JwtResponse signIn(User user);

}
