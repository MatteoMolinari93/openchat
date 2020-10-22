package com.molim.cleancoders.openchat.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		ifUserDoesNotExistThrowException(postToSave.getUserId());
		return postMapper.PostToPostDto(postRepository.save(postMapper.PostDtoToPost(postToSave)));
	}

	@Override
	public List<PostDto> getPostsForUser(Long userId) {
		ifUserDoesNotExistThrowException(userId);
		return postRepository.findByUserId(userId).stream().map(post -> postMapper.PostToPostDto(post)).collect(Collectors.toList());
	}

	private void ifUserDoesNotExistThrowException(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isEmpty()) {
			throw new UserDoesNotExistException();
		}
	}

}
