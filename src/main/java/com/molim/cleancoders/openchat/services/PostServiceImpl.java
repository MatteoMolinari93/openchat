package com.molim.cleancoders.openchat.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.molim.cleancoders.openchat.data.entities.User;
import com.molim.cleancoders.openchat.data.repositories.PostRepository;
import com.molim.cleancoders.openchat.data.repositories.UserRepository;
import com.molim.cleancoders.openchat.exceptions.UserDoesNotExistException;
import com.molim.cleancoders.openchat.web.mappers.PostMapper;
import com.molim.cleancoders.openchat.web.models.PostDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
	
	private final PostMapper postMapper;
	
	private final PostRepository postRepository;
	
	private final UserRepository userRepository;

	@Override
	@Transactional
	public PostDto createPost(PostDto postToSave) {
		Optional<User> user = userRepository.findById(postToSave.getUserId());
		
		if(user.isEmpty()) {
			throw new UserDoesNotExistException();
		}
		
		return postMapper.PostToPostDto(postRepository.save(postMapper.PostDtoToPost(postToSave)));
	}

}
