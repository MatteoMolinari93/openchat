package com.molim.cleancoders.openchat.web.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.molim.cleancoders.openchat.services.PostService;
import com.molim.cleancoders.openchat.web.models.PostDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostController {
	
	@Autowired
	private final PostService postService;
	
	@PostMapping("/openchat/user/{id}/post")
	ResponseEntity<PostDto> register(@PathParam("id") final Long userId, @RequestBody PostDto postDto) {
		return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED);
	}
}
