package com.bookmytrip.exception;

public class InsufficientSeatsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InsufficientSeatsException(String message) {
		super(message);
	}

}
