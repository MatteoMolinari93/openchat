package com.molim.cleancoders.openchat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Test")
public class UsernameAlreadyInUseException extends RuntimeException {
	
	public UsernameAlreadyInUseException() {
		super("Username already in use.");
	}

}
