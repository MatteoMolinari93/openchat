package com.molim.cleancoders.openchat.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.molim.cleancoders.openchat.data.repositories.PostRepository;

@DataJpaTest
public class PostRepositoryTest {
	
	@Autowired
	private PostRepository postRepository;

	@Test
	void getPostByUserId() {
		assertEquals(postRepository.findByUserId(12L).size(), 2);
	}
	
}
