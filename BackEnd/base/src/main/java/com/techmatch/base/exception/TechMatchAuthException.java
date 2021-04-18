package com.techmatch.base.exception;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TechMatchAuthException extends TechMatchException{
	List< String> errorMessages;
	public TechMatchAuthException() {
		super();
	}
	public TechMatchAuthException(final String message) {
		super(message);
	}
	public TechMatchAuthException(final Throwable cause) {
		super(cause);
	}
	public TechMatchAuthException(final String message,final Throwable cause) {
		super(message,cause);
	}
	public TechMatchAuthException(List<String> errorMessages) {
		this.errorMessages=errorMessages;
	}
}
