package com.ee.Exception;

public class UnKnownUserTypeException extends BaseException {

	private static final long serialVersionUID = -1L;

	/**
	 * @param message
	 * @param errorCode
	 */
	public UnKnownUserTypeException(String message, int errorCode) {
		super(message, errorCode);
	}

	/**
	 * @param message
	 */
	public UnKnownUserTypeException(String message) {
		super(message);
	}

}
