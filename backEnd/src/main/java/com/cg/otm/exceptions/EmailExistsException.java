package com.cg.otm.exceptions;

public class EmailExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EmailExistsException(String message) {
		super(message);
	}
	
	public EmailExistsException(String message,Throwable t)
	{
		super(message,t);
	}
	
	
	
}
