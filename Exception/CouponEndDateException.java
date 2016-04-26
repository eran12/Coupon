package com.ee.Exception;


public class CouponEndDateException extends BaseException {

	private static final long serialVersionUID = -1L;

	public CouponEndDateException(String message) {
		super(message);
	}

	public CouponEndDateException(String message, int errorCode) {
		super(message, errorCode);
	}

}
