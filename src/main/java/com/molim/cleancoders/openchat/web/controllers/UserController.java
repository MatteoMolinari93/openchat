package com.molim.cleancoders.openchat.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molim.cleancoders.openchat.services.UserService;
import com.molim.cleancoders.openchat.web.models.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/users")
	CollectionModel<UserDto> getUsers() {
		List<UserDto> users = userService.getUsers();
		System.out.println("Controller: " + users.size());
		users.stream().forEach(user -> user.removePassword());
		return CollectionModel.of(users, new ArrayList<Link>());
	}

}
