package com.molim.cleancoders.openchat.web.models;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto extends RepresentationModel<UserDto> {
	
	private final Long id;
	private final String username;
	
	@JsonInclude(Include.NON_NULL)
	private final String password;
	private final String about;

}
