package com.molim.cleancoders.openchat.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	
	@Id
	@Column
	private Long id;
	@Column
	private Long userId;
	@Column
	private String text;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
}
