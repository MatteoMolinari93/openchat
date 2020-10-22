package com.molim.cleancoders.openchat.web.controllers;

import java.util.ArrayList;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.molim.cleancoders.openchat.services.PostService;
import com.molim.cleancoders.openchat.web.models.PostDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostController {
	
	private final PostService postService;
	
	@PostMapping("/user/{id}/posts")
	ResponseEntity<PostDto> register(@PathVariable("id") final Long userId, @RequestBody PostDto postDto) {
		postDto.setUserId(userId);
		postDto.setId(null);
		return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	
	@GetMapping("/user/{id}/posts")
	CollectionModel<PostDto> getUserPosts(@PathVariable("id") final Long userId) {
		return CollectionModel.of(postService.getPostsForUser(userId), new ArrayList<Link>());
	}
}
