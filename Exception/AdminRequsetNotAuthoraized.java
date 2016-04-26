package com.ee.Exception;

public class AdminRequsetNotAuthoraized extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	/**
	 * @param message
	 * @param errorCode
	 */
	public AdminRequsetNotAuthoraized(String message, int errorCode) {
		super(message, errorCode);
	}

	/**
	 * @param message
	 */
	public AdminRequsetNotAuthoraized(String message) {
		super(message);
	}

}
