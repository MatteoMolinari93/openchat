package com.molim.cleancoders.openchat.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.molim.cleancoders.openchat.data.repositories.UserRepository;

@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;

	@Test
	void getUserById() {
		assertTrue(userRepository.findById(12L).isPresent());
	}
	
	@Test
	void findUserByUsername() {
		assertNotNull(userRepository.findByUsername("Matteo"));
	}
	
	@Test
	void findUserByUsernameAndPassword() {
		assertNotNull(userRepository.findByUsernameAndPassword("Matteo", "1234StrongPassword"));
	}
	
}
