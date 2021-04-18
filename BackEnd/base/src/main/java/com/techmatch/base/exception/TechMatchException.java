package com.techmatch.base.exception;

import java.util.Map;

import lombok.Data;

@Data
public class TechMatchException extends Exception{
	/**
　　      *  シリアルID
	 */
	private static final long serialVersionUID = 9215843516060463248L;

	public TechMatchException() {
		super();
	}
	public TechMatchException(final String message) {
		super(message);
	}
	public TechMatchException(final Throwable cause) {
		super(cause);
	}
	public TechMatchException(final String message,final Throwable cause) {
		super(message,cause);
	}
}
