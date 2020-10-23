package com.molim.cleancoders.openchat.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.molim.cleancoders.openchat.data.entities.User;
import com.molim.cleancoders.openchat.data.repositories.UserRepository;
import com.molim.cleancoders.openchat.exceptions.UsernameAlreadyInUseException;
import com.molim.cleancoders.openchat.web.mappers.UserMapper;
import com.molim.cleancoders.openchat.web.models.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;
	
	@Override
	public UserDto registerUser(UserDto user) {
		Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
		if(foundUser.isPresent()) {
			throw new UsernameAlreadyInUseException();
		}
		
		UserDto registeredUser = userMapper.UserToUserDto(userRepository.save(userMapper.UserDtoToUser(user)));
		registeredUser.removePassword();
		return registeredUser;
	}

}
