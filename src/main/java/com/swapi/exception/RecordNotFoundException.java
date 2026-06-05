package com.swapi.exception;

public class RecordNotFoundException extends RuntimeException{
	public RecordNotFoundException(String message) {
	    super(message);
	}
}
