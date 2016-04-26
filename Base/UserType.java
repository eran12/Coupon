package com.ee.Base;
/**
 * this class is a part of the Coupon System fundamental.</br>
 * if user class is add to the system it is must to be add also to here.</br></br>
 * 
 * <Strong> Note: </strong> the String of the attribute is also will be the name of the
 * table inside the <tt>DB</tt>.</br>
 * if the BaseUser will not be in here as a UserType the system will throw Exceptions.
 * @author  Yossef Eran Eichenbaum
 *
 */

public enum UserType {
	
	// Attributes 
	/**
	 * "customer"
	 */
	CUSTOMER("customer"),
	/**
	 * "company"
	 */
	COMPANY("company"),
	/**
	 * "admin"
	 */
	ADMIN("admin");

	private String type;
	
	// Constructor
	private UserType(String type){
		this.type = type;
	}

	/**
	 * 
	 * @return String of the given attribute.
	 */
	public String getType(){
		return type;
	}

}
