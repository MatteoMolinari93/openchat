package com.molim.cleancoders.openchat.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.molim.cleancoders.openchat.data.entities.User;
import com.molim.cleancoders.openchat.data.repositories.UserRepository;
import com.molim.cleancoders.openchat.exceptions.InvalidCredentialsException;
import com.molim.cleancoders.openchat.web.mappers.UserMapper;
import com.molim.cleancoders.openchat.web.models.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;

	@Override
	public UserDto loginUser(UserDto capture) {
		Optional<User> foundUser = userRepository.findByUsernameAndPassword(capture.getUsername(), capture.getPassword());
		
		if(foundUser.isEmpty()) {
			throw new InvalidCredentialsException();
		}
		
		UserDto userDto = userMapper.UserToUserDto(foundUser.get());
		userDto.removePassword();
		return userDto;
	}

}
