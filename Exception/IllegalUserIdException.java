package com.ee.Exception;

public class IllegalUserIdException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	
	public IllegalUserIdException(String message) {
		super(message);
	}
	
	public IllegalUserIdException(String message, int errorCode) {
		super(message, errorCode);
	}
}
