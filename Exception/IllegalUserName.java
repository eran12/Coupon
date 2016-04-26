package com.ee.Exception;

public class IllegalUserName extends BaseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public IllegalUserName(String message) {
		super(message);
	}
	
	public IllegalUserName(String message, int errorode){
		super(message, errorode);
	}

}
