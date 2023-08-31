package com.wolfhack.todo.controller.implement;

import com.wolfhack.todo.controller.SignUpEndpoints;
import com.wolfhack.todo.mapper.WebUserMapper;
import com.wolfhack.todo.model.User;
import com.wolfhack.todo.model.create.UserCreateDTO;
import com.wolfhack.todo.service.IAuthenticationService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-up")
@RequiredArgsConstructor
public class SignUpController implements SignUpEndpoints {

	private final IAuthenticationService authenticationService;
	private final WebUserMapper userMapper;

	@Override
	public ResponseEntity<?> signUp(UserCreateDTO userCreateDTO) {
		User user = userMapper.toModel(userCreateDTO);
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, authenticationService.signUp(user).token())
				.build();
	}

	@RolesAllowed({ "USER", "ADMIN" })
	@GetMapping("/test")
	public String test() {
		return "Successfully signed up";
	}

}
