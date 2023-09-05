package com.wolfhack.todo.controller.implement;

import com.wolfhack.todo.annotation.Endpoint;
import com.wolfhack.todo.controller.SignInEndpoints;
import com.wolfhack.todo.mapper.WebUserMapper;
import com.wolfhack.todo.model.User;
import com.wolfhack.todo.model.create.UserLoginDTO;
import com.wolfhack.todo.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-in")
@RequiredArgsConstructor
public class SignInController implements SignInEndpoints {

	private final IAuthenticationService authenticationService;
	private final WebUserMapper userMapper;

	@Override
	@Endpoint
	public ResponseEntity<?> signIn(UserLoginDTO userLoginDTO) {
		User user = userMapper.toModel(userLoginDTO);
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, authenticationService.signIn(user).token())
				.build();
	}
}
