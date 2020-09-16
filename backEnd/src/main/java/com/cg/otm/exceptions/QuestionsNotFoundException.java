package com.cg.otm.exceptions;

public class QuestionsNotFoundException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuestionsNotFoundException() {
	
	}
	
	public QuestionsNotFoundException(String message)
	{
	super(message);
	}
}
