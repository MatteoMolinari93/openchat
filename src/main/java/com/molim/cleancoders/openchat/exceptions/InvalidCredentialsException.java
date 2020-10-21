package com.molim.cleancoders.openchat.exceptions;

public class InvalidCredentialsException extends BadRequestException {
	
	public InvalidCredentialsException() {
		super("Invalid credentials.");
	}

}
