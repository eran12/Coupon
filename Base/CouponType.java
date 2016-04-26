package com.ee.Base;
/**
 * this base class is for the {@link Coupon} class.</br>
 * {@code CouponType} holds the variables of the types that coupons can hold.
 * @author Yossef Eran Eichenbaum
 *
 */

public enum CouponType {

	
	RESTURANT ("RESTURANT"),
	ELECTRICITY ("ELECTRICITY"),
	FOOD ("FOOD"),
	HEALTH ("HEALTH"),
	SPORTS ("SPORTS"),
	CAMPING ("CAMPING"),
	TRAVELLING ("TRAVELLING"),
	BABY("BABY");

	private String name;

	/**
	 * 
	 * @param name String.
	 */
	private CouponType(String name) {
		this.name = name;
	}

	/**
	 * @return String with the E-num name of the coupon type.
	 */
	public String getName()
	{
		return name;
	}
}
