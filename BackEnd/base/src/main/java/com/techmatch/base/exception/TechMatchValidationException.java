package com.techmatch.base.exception;

import java.util.Map;

import lombok.Data;

@Data
public class TechMatchValidationException extends TechMatchException{
	/**
	 * シリアルID
	 */
	private static final long serialVersionUID = 1L;
	Map<String, String> errorMessages;
	String errorMessage;
	public TechMatchValidationException(Map<String, String> errorMessages) {
		this.errorMessages=errorMessages;
	}
	public TechMatchValidationException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
