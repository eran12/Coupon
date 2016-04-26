package com.ee.Dao;

import java.time.LocalDate;
import java.util.Collection;

import com.ee.Base.Coupon;
import com.ee.Base.CouponType;
import com.ee.Base.UserBase;
import com.ee.Exception.BaseException;

public interface CouponDao {

	/**
	 * this method should return Collections of Coupons that User have by the join table in the DB.
	 * 
	 * @param user {@link UserBase}
	 * @return Collection of all user Coupons by the join table.
	 * @throws BaseException
	 */
	public Collection<Coupon> getAllUserCoupons(UserBase user) throws BaseException;

	/**
	 * this method shoould return a {@link Coupon} object from the DB by the given id.</br>
	 * @param id long.
	 * @return {@link Coupon} object.
	 * @throws BaseException
	 */
	public Coupon getCouponById(long id) throws BaseException;

	/**
	 * this method should return a Collection of all the Coupons that exist in the DB.</br>
	 * @return Collection of all the Coupons in the DB.</br>
	 * @throws BaseException
	 */
	public Collection<Coupon> getAllCoupons() throws BaseException;

	/**
	 * the method should return a Collection of all the coupons by the company id that create them.</br>
	 * it takes the id`s from the company_coupon join table.</br>
	 * the method throws a {@link BaseException} if there is no such company in that id.
	 * @param id long.
	 * @return Collection of {@link Coupon} only for the given company id.
	 * @throws BaseException
	 */
	public Collection<Coupon> getCouponsByCompany(long id) throws BaseException;

	/**
	 * this method should give back a collection with all the coupons that is up to the given date.
	 * @param maxStartDate {@link LocalDate}
	 * @return Collection with all the coupons from the max start date to the lower start date. 
	 * @throws BaseException
	 */
	public Collection<Coupon> getCouponsByMinStartDate(LocalDate minStartDate) throws BaseException;

	/**
	 * this method should give back a collection with all the coupons that is up to the given date.
	 * @param maxEndDate {@link LocalDate}
	 * @return Collection with all the coupons from the max end date to the lower end date.
	 * @throws BaseException
	 */
	public Collection<Coupon> getCouponsByMaxEndDate(LocalDate maxEndDate) throws BaseException;

	/**
	 * the method should return a Collection with all the coupons up to the max price that given.
	 * @param maxPrice
	 * @return Collection with all the coupons from the max price to the lower price.
	 * @throws BaseException
	 */
	public Collection<Coupon> getCouponsByMaxPrice(double maxPrice) throws BaseException;

	/**
	 * the method should give all the coupons with the specific {@link CouponType}.
	 * @param startDate {@link LocalDate}
	 * @return Collection with all the Coupons by the {@link CouponType}.
	 * @throws BaseException
	 */
	public Collection<Coupon> getCouponsByType(CouponType type) throws BaseException;

	/**
	 * this method should remove a coupon id from all of the joins tables in the DB. 
	 * @param coupon {@link Coupon}.
	 * @throws BaseException
	 */
	public void removeFromJoinUserCouponTable(Coupon coupon) throws BaseException;

	/**
	 * this method should insert the id of the user and the coupon to a join table.</br>
	 * @param user {@link UserBase}
	 * @param coupon {@link Coupon}
	 * @throws BaseException
	 */
	public void insertToJoinUserCouponTable(UserBase user, Coupon coupon) throws BaseException;

	public Collection<Coupon> getCouponsLikeTitle(String couponTitle) throws BaseException;
	
	public Coupon getCouponByTitle(String couponTitle) throws BaseException;

}
