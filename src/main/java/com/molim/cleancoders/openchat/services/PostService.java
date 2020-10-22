package com.molim.cleancoders.openchat.services;

import java.util.List;

import com.molim.cleancoders.openchat.web.models.PostDto;

public interface PostService {

	PostDto createPost(PostDto postToSave);

	List<PostDto> getPostsForUser(Long userId);


}
