package com.molim.cleancoders.openchat.web.models;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@Relation(collectionRelation = "posts", itemRelation = "post")
public class PostDto extends RepresentationModel<PostDto> {
	
	@Setter
	private Long id;
	@Setter
	private Long userId;
	private final String text;
	private final Date dateTime;
	
}
