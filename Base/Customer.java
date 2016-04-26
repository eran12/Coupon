package com.ee.Base;

import com.ee.Exception.BaseException;


public class Customer extends UserBase {

	// Constructor

	/**
	 * this constructor send the {@link UserType} of <tt>CUSTOMER</tt> to the {@link UserBase} class.</br>
	 * this is how the UserBase know how to treat with all of the DB and Facade classes.</br>
	 */
	public Customer(){
		super(UserType.CUSTOMER);
	}
	
	/**
	 * this constructor send the data to the constructor inside the {@link UserBase} class.</br>
	 * the method send automatic the {@link UserType} <tt>CUSTOMER</tt>.</br>
	 * when the object of Customer is created it wont require the UserType of CUSTOMER.
	 * 
	 * 
	 * @param userName String.
	 * @param password String.
	 * @param email String.
	 * @throws BaseException
	 */
	public Customer(String customerName, String password,  String email) throws BaseException{
		super(UserType.CUSTOMER,customerName, password, email);
	}

	/**
	 * this method is needed for the DB.
	 * the method send the {@link UserType} string as it written in the <tt>E-NUM</tt>
	 * 
	 * @return String of "customer".
	 */
	@Override
	public String getUserTableName() {
		return UserType.CUSTOMER.getType().toLowerCase();
	}

}
