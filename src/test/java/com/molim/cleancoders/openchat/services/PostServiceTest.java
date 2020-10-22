package com.molim.cleancoders.openchat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Date;
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

import com.molim.cleancoders.openchat.data.entities.Post;
import com.molim.cleancoders.openchat.data.entities.User;
import com.molim.cleancoders.openchat.data.repositories.PostRepository;
import com.molim.cleancoders.openchat.data.repositories.UserRepository;
import com.molim.cleancoders.openchat.exceptions.UserDoesNotExistException;
import com.molim.cleancoders.openchat.web.mappers.PostMapper;
import com.molim.cleancoders.openchat.web.models.PostDto;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
	
	@Captor
	private ArgumentCaptor<Long> userIdCaptor;
		
	@Mock
	private PostRepository postRepository;
	@Mock
	private UserRepository userRepository;
	
	@Spy
	private PostMapper postMapper = Mappers.getMapper(PostMapper.class);
	
	@InjectMocks
	private PostServiceImpl postService;

	@Test
	void createPostSuccessful() {
		User user = new User(13L, "Matteo", null, null);
		Post savedPost = new Post(233l, user.getId(), "", new Date());
		PostDto postToSave = new PostDto(null, user.getId(), "", new Date());
		
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(postRepository.save(any(Post.class))).thenReturn(savedPost);
		
		PostDto savedPostDto = postService.createPost(postToSave);
		
		assertNotNull(savedPostDto);
		assertEquals(savedPost.getId(), savedPostDto.getId());
		assertEquals(savedPost.getUserId(), savedPostDto.getUserId());
		assertEquals(savedPost.getText(), savedPostDto.getText());
		assertEquals(savedPost.getDateTime(), savedPostDto.getDateTime());
		
		verify(userRepository).findById(user.getId());
		verify(postRepository).save(any(Post.class));
	}
	
	@Test
	void createPostForNotExistentUserThrowsException() {	
		PostDto postToSave = new PostDto(null, 222L, "", new Date());
		
		when(userRepository.findById(userIdCaptor.capture())).thenReturn(Optional.empty());		
		
		assertThrows(UserDoesNotExistException.class, () -> postService.createPost(postToSave));
		
		assertEquals(postToSave.getUserId(), userIdCaptor.getValue());
				
		verify(userRepository).findById(postToSave.getUserId());
	}
	
}
