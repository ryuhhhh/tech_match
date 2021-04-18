package com.techmatch.base.exception;

public class TechMatchDataException extends TechMatchException{
	public TechMatchDataException() {
		super();
	}
	public TechMatchDataException(final String message) {
		super(message);
	}
	public TechMatchDataException(final Throwable cause) {
		super(cause);
	}
	public TechMatchDataException(final String message,final Throwable cause) {
		super();
	}
}
