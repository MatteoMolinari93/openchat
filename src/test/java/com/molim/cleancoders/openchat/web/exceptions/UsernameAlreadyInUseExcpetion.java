package com.molim.cleancoders.openchat.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Test")
public class UsernameAlreadyInUseExcpetion extends RuntimeException {
	
	public UsernameAlreadyInUseExcpetion() {
		super("Username already in use.");
	}

}
