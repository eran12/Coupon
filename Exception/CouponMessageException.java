package com.ee.Exception;

public class CouponMessageException extends BaseException {

	private static final long serialVersionUID = -1L;

	public CouponMessageException(String message) {
		super(message);
	}

	public CouponMessageException(String message, int errorCode) {
		super(message, errorCode);
	}
}
