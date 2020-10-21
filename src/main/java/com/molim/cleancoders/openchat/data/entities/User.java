package com.molim.cleancoders.openchat.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Entity
public class User {
	
	@Id
	private final Long id;
	@Column(name = "username", nullable = false)
	private final String username;
	@Column(name = "password", nullable = false)
	private final String password;
	@Column(name = "about")
	private final String about;

}
