package com.ee.Dao;

import com.ee.Base.Coupon;
import com.ee.Base.Customer;
import com.ee.Exception.BaseException;

/**
 * this class extends the {@link UserDao} class so it will get all of the user functions.</br>
 * plus the functions that are unique to the {@link Customer} user. 
 * @author Yossef Eran Eichenbaum.
 *
 */
public interface CustomerDao extends UserDao {

	/**
	 * this method is only for Customers because only they can buy coupons.</br>
	 * the method should reduce -1 for the amount of the coupon.<br>
	 * it throws {@link BaseException} when the amount is 0.
	 * <strong>NOTE:</strong> this method should be synchronized. </br>
	 * @param coupon Coupon.
	 * @throws BaseException
	 */
	public void purchaseCoupon(Coupon coupon) throws BaseException;
}