package com.molim.cleancoders.openchat.web.models;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDto extends RepresentationModel<PostDto> {
	
	private final Long id;
	private final Long userId;
	private final String text;
	private final LocalDateTime dateTime;
	
}
