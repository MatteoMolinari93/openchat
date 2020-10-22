package com.molim.cleancoders.openchat.web.models;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PostDto extends RepresentationModel<PostDto> {
	
	@Setter
	private Long id;
	@Setter
	private Long userId;
	private final String text;
	private final LocalDateTime dateTime;
	
}
