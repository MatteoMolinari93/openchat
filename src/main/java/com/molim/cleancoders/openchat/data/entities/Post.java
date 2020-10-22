package com.molim.cleancoders.openchat.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Entity
public class Post {
	
	@Id
	@Column
	private final Long id;
	@Column
	private final Long userId;
	@Column
	private final String text;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private final Date dateTime;
}
