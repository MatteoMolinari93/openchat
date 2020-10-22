package com.molim.cleancoders.openchat.web.mappers;

import org.mapstruct.Mapper;

import com.molim.cleancoders.openchat.data.entities.Post;
import com.molim.cleancoders.openchat.web.models.PostDto;

@Mapper
public interface PostMapper {
	
	PostDto PostToPostDto(Post post);
	
	Post PostDtoToPost(PostDto postDto);

}
