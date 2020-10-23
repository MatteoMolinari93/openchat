package com.molim.cleancoders.openchat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import com.molim.cleancoders.openchat.web.mappers.UserMapper;
import com.molim.cleancoders.openchat.web.models.UserDto;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Captor
	private ArgumentCaptor<Long> userIdCaptor;
		
	@Mock
	private UserRepository userRepository;
	
	@Spy
	private UserMapper postMapper = Mappers.getMapper(UserMapper.class);
	
	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void getUsers() {
		List<User> users = new ArrayList<User>();
		users.add(new User(1L, "Matteo", "psw1", ""));
		users.add(new User(13L, "Marta", "psw2", ""));
		
		when(userRepository.findAll()).thenReturn(users);
		
		List<UserDto> readUsers = userService.getUsers();
		
		assertNotNull(readUsers);
		assertEquals(readUsers.size(), 2);
		
		verify(userRepository).findAll();
	}
	
}
