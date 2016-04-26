package com.ee.Base;

import com.ee.Exception.BaseException;

/**
 * this class represent Company in the Coupon System.</br>
 * it extends the class {@link UserBase}.
 * @author Yossef Eran Eichenbaum
 *
 */
public class Company extends UserBase {

	// Constructor

	/**
	 * this constructor send the {@link UserType} of <tt>COMPANY</tt> to the {@link UserBase} class.</br>
	 * this is how the UserBase know how to treat with all of the DB and Facade classes.</br>
	 */
	public Company(){
		super(UserType.COMPANY);
	}

	/**
	 * this constructor send the data to the constructor inside the {@link UserBase} class.</br>
	 * the method send automatic the {@link UserType} <tt>COMPANY</tt>.</br>
	 * when the object of Company is created it wont require the UserType of COMPANY.
	 * 
	 * 
	 * @param userName String.
	 * @param password String.
	 * @param email String.
	 * @throws BaseException
	 */
	public Company(String companyName,String email, String password) throws BaseException{
		super(UserType.COMPANY,companyName, password, email);
	}

	/**
	 * this method is needed for the DB.
	 * the method send the {@link UserType} string as it written in the <tt>E-NUM</tt>
	 * 
	 * @return String of "company".
	 */
	@Override
	public String getUserTableName() {
		return UserType.COMPANY.getType().toLowerCase();
	}

}
