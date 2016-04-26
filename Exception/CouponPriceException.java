package com.ee.Exception;


public class CouponPriceException extends BaseException {

	private static final long serialVersionUID = -1L;

	public CouponPriceException(String message) {
		super(message);
	}

	public CouponPriceException(String message, int errorCode) {
		super(message, errorCode);
	}
}
