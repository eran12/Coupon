package com.ee.Exception;

public class ConnectingUserDBException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public ConnectingUserDBException(String message) {
		super(message);
	}
	public ConnectingUserDBException(String message, int errorCode) {
		super(message, errorCode);
	}
}
