package com.ee.Exception;

public class CouponExpireThreadInterrupedException extends BaseException {

	private static final long serialVersionUID = -1L;

	public CouponExpireThreadInterrupedException(String message) {
		super(message);
	}

	public CouponExpireThreadInterrupedException(String message, int errorCode) {
		super(message, errorCode);
		// TODO Auto-generated constructor stub
	}

}
