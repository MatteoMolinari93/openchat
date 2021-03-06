package com.molim.cleancoders.openchat.web.models;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserDto extends RepresentationModel<UserDto> {
		
	private final Long id;
	private final String username;
	
	@JsonInclude(Include.NON_NULL)
	private String password;
	private final String about;
	
	public void removePassword() {
		this.password = null;
	}

}
