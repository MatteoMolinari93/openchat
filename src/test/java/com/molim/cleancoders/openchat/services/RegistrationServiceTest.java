package com.molim.cleancoders.openchat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.molim.cleancoders.openchat.data.entities.User;
import com.molim.cleancoders.openchat.data.repositories.UserRepository;
import com.molim.cleancoders.openchat.exceptions.UsernameAlreadyInUseException;
import com.molim.cleancoders.openchat.web.mappers.UserMapper;
import com.molim.cleancoders.openchat.web.models.UserDto;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
	
	@Captor
	private ArgumentCaptor<User> userCaptor;
	
	@Mock
	private UserRepository userRepository;
	
	@Spy
	private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
	
	@InjectMocks
	private RegistrationServiceImpl registrationService;

	@Test
	void registerUserReturnsCorrectDto() {
		
		UserDto userDto = UserDto.builder()
				.username("Matteo")
				.password("MyPassword")
				.about("I love to code")
				.build();
		
		User savedUser = User.builder()
				.username("Matteo")
				.password("MyPassword")
				.about("I love to code.")
				.build();
		
		when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.empty());
		when(userRepository.save(userCaptor.capture())).thenReturn(savedUser);
		
		UserDto registeredUser = registrationService.registerUser(userDto);
		
		assertEquals(userCaptor.getValue().getUsername(), registeredUser.getUsername());
		assertNull(registeredUser.getPassword());
		
		verify(userRepository).findByUsername(userDto.getUsername());
		verify(userRepository).save(any(User.class));
	}
	
	@Test
	void registerUserAlreadyPresentThrowsException() {	
		UserDto userDto = UserDto.builder()
				.username("Matteo")
				.password("MyPassword")
				.about("I love to code")
				.build();
		
		User user = User.builder()
				.username("Matteo")
				.password("MyPassword")
				.about("I love to code.")
				.build();
		
		when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.of(user));		
		
		assertThrows(UsernameAlreadyInUseException.class, () -> registrationService.registerUser(userDto));
				
		verify(userRepository).findByUsername(userDto.getUsername());
		verify(userRepository, never()).save(any(User.class));
	}
	
}
