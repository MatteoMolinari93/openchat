package com.molim.cleancoders.openchat.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.molim.cleancoders.openchat.data.repositories.UserRepository;
import com.molim.cleancoders.openchat.web.mappers.UserMapper;
import com.molim.cleancoders.openchat.web.models.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;

	@Override
	public List<UserDto> getUsers() {
		System.out.println("Get users");
		List<UserDto> users = new ArrayList<UserDto>();
		System.out.println("Service: " + users.size());
		userRepository.findAll().forEach(dbUser -> users.add(userMapper.UserToUserDto(dbUser)));
		return users;
	}
	
	
}
