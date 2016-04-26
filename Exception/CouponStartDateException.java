package com.ee.Exception;

public class CouponStartDateException extends BaseException {

	private static final long serialVersionUID = -1L;

	public CouponStartDateException(String message) {
		super(message);
	}

	public CouponStartDateException(String message, int errorCode) {
		super(message, errorCode);
	}

}
