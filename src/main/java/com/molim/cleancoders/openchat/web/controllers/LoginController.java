package com.molim.cleancoders.openchat.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.molim.cleancoders.openchat.services.LoginService;
import com.molim.cleancoders.openchat.web.models.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LoginController {
	
	@Autowired
	private final LoginService loginService;
	
	@PostMapping("/openchat/login")
	UserDto register(@RequestBody UserDto user) {
		UserDto loggedInUser = loginService.loginUser(user);
		loggedInUser.removePassword();
		
		return loggedInUser;
	}
}
