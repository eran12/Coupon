package com.ee.Dao;

import com.ee.Base.Company;
import com.ee.Base.Coupon;
import com.ee.Exception.BaseException;

/**
 * this class extends the {@link UserDao} class so it will get all of the user functions.</br>
 * plus the functions that are unique to the {@link Company} user. 
 * @author Yossef Eran Eichenbaum.
 *
 */
public interface CompanyDao extends UserDao {
	
	/**
	 * this method should create a {@link Coupon} object in the DB.</br>
	 * @param coupon {@link Coupon}.
	 * @throws BaseException
	 */
	public void createCoupon(Coupon coupon) throws BaseException;

	/**
	 * this method should remove a {@link Coupon} object from the DB.</br>
	 * @param coupon {@link Coupon}.
	 * @throws BaseException
	 */
	public void removeCoupon(Coupon coupon) throws BaseException;

	/**
	 * this method should update the {@link Coupon} object in the DB.</br>
	 * <strong> NOTE: </strong> the id of the coupon cannot be change.
	 * @param coupon {@link Coupon}.
	 * @throws BaseException
	 */
	public void upDateCoupon(Coupon coupon) throws BaseException;

}
