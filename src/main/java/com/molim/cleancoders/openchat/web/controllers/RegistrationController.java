package com.molim.cleancoders.openchat.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.molim.cleancoders.openchat.services.RegistrationService;
import com.molim.cleancoders.openchat.web.models.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RegistrationController {
	
	@Autowired
	private final RegistrationService registrationService;
	
	@PostMapping("/openchat/registration")
	ResponseEntity<UserDto> register(@RequestBody UserDto user) {
		UserDto savedUser = registrationService.registerUser(user);
		
		return new ResponseEntity<UserDto>(savedUser, HttpStatus.CREATED);
	}
	

}
