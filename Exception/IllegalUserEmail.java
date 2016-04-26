package com.ee.Exception;

public class IllegalUserEmail extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public IllegalUserEmail(String message) {
		super(message);
	}
	
	public IllegalUserEmail(String message, int errorCode){
		super(message,errorCode);
	}
}
