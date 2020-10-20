package com.molim.cleancoders.openchat.web.models;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto extends RepresentationModel<UserDto> {
	
	private final Long id;
	private final String username;
	private final String password;
	private final String about;

}
