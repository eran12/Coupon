package com.ee.Exception;

public class UserDoNotExistExeption extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public UserDoNotExistExeption(String message) {
		super(message);
	}

	public UserDoNotExistExeption(String message, int errorCode) {
		super(message, errorCode);
	}

}
