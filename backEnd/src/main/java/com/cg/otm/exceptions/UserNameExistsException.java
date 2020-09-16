package com.cg.otm.exceptions;

public class UserNameExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserNameExistsException(String message) {
		super(message);
	}
	
	public UserNameExistsException(String message,Throwable t)
	{
		super(message,t);
	}
	
	
	
}
