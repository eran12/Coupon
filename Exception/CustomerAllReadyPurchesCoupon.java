package com.ee.Exception;

public class CustomerAllReadyPurchesCoupon extends BaseException {

	private static final long serialVersionUID = -1L;

	public CustomerAllReadyPurchesCoupon(String message) {
		super(message);
	}

	public CustomerAllReadyPurchesCoupon(String message, int errorCode) {
		super(message, errorCode);
	}

}
