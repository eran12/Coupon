package com.ee.Exception;

public class CouponAmountException extends BaseException {

	private static final long serialVersionUID = -1L;

	public CouponAmountException(String message) {
		super(message);
	}

	public CouponAmountException(String message, int errorCode) {
		super(message, errorCode);
	}

}
