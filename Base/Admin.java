package com.ee.Base;

import com.ee.Exception.BaseException;

/**
 * this base class represent the System administer.</br>
 * it extends the class {@link UserBase}.
 * @author Yossef Eran Eichenbaum
 *
 */
public class Admin extends UserBase {

	// Constructor

		/**
		 * this constructor send the {@link UserType} of <tt>ADMIN</tt> to the {@link UserBase} class.</br>
		 * this is how the UserBase know how to treat with all of the DB and Facade classes.</br>
		 */
	public Admin() {
		super(UserType.ADMIN);
	}

	/**
	 * this constructor send the data to the constructor inside the {@link UserBase} class.</br>
	 * the method send automatic the {@link UserType} <tt>ADMIN</tt>.</br>
	 * when the object of Admin is created it wont require the UserType of ADMIN.
	 * 
	 * 
	 * @param userName String.
	 * @param password String.
	 * @param email String.
	 * @throws BaseException
	 */
	public Admin(String adminName, String password, String email) throws BaseException {
		super(UserType.ADMIN,adminName, password, email);
	}
	
	/**
	 * this will return a String "admin". as it written in the {@link UserType} class.
	 * @return the table name in the DB = "admin".</br>
	 * as it written in the {@link UserType} class. 
	 */
	@Override
	public String getUserTableName() {
		return UserType.ADMIN.getType().toLowerCase();
	}
	
	/**
	 * this method is needed for the DB.</br>
	 * the method send the {@link UserType} string as it written in the <tt>E-NUM</tt>.</br></br>
	 * 
	 * <strong>NOTE:</STRONG> admin cannot buy coupons so the method will return NULL. 
	 * 
	 * @return <strong>null</strong> - because admin can not buy coupons.
	 */
	@Override
	public String getCouponTableName() {
		return null;
	}

}
