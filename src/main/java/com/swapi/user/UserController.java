package com.swapi.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.auth.dto.CustomUserPrincipal;
import com.swapi.user.dto.UserDetailsDtoResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
	private final UserService userService;
	
	@GetMapping("/profile/me")
	public UserDetailsDtoResponse getProfileSelfInformation(@AuthenticationPrincipal CustomUserPrincipal user) {
		return userService.getDetailsById(user.getId());
	}
}
