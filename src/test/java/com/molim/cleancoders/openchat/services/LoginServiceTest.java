package com.molim.cleancoders.openchat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.molim.cleancoders.openchat.exceptions.InvalidCredentialsException;
import com.molim.cleancoders.openchat.web.mappers.UserMapper;
import com.molim.cleancoders.openchat.web.models.UserDto;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
	
	@Captor
	private ArgumentCaptor<String> usernameCaptor;
	@Captor
	private ArgumentCaptor<String> passwordCaptor;
	
	@Mock
	private UserRepository userRepository;
	
	@Spy
	private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
	
	@InjectMocks
	private LoginServiceImpl loginService;

	@Test
	void loginUserReturnsCorrectDto() {
		
		UserDto userDto = UserDto.builder()
				.username("Matteo")
				.password("MyPassword")
				.about("I love to code")
				.build();
		
		User savedUser = User.builder()
				.id(1L)
				.username("Matteo")
				.password("MyPassword")
				.about("I love to code.")
				.build();
		
		when(userRepository.findByUsernameAndPassword(usernameCaptor.capture(), passwordCaptor.capture())).thenReturn(Optional.of(savedUser));
		
		UserDto loggedInUser = loginService.loginUser(userDto);
		
		assertNotNull(loggedInUser);
		assertNotNull(loggedInUser.getId());
		assertNull(loggedInUser.getPassword());
		assertEquals(usernameCaptor.getValue(), loggedInUser.getUsername());
		
		verify(userRepository).findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());

	}
	
	@Test
	void loginUserWithWrongCredentialsThrowsException() {	
		UserDto userDto = UserDto.builder()
				.username("Matteo")
				.password("MyPassword")
				.about("I love to code")
				.build();
		
		when(userRepository.findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword())).thenReturn(Optional.empty());		
		
		assertThrows(InvalidCredentialsException.class, () -> loginService.loginUser(userDto));
				
		verify(userRepository).findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
	}
	
}
