package com.cg.otm.exceptions;

public class NoDataFoundedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NoDataFoundedException() {
		super();
	}
	
	public NoDataFoundedException(String msg) {
		super(msg);
	}

}
