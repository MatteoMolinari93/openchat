package com.molim.cleancoders.openchat.exceptions;

public abstract class BadRequestException extends RuntimeException {

	public BadRequestException(String string) {
		super(string);
	}

}
