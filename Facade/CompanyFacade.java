package com.ee.Facade;

import java.util.Collection;

import com.ee.Base.Coupon;
import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.DBDao.CompanyDBDao;
import com.ee.DBDao.UserDBDao;
import com.ee.Dao.CompanyDao;
import com.ee.Exception.ConnectingUserDBException;
import com.ee.Exception.CouponTitleException;

/**
 * this class implements the {@link CompanyDao} class.</br>
 * this is because if the DBDao layer will be replaced every thing will keep on working.</br>
 * this class is the Company Facade in the system and all of the functions that a company user can do.</br> 
 * @author Yossef Eran Eichenbaum
 *
 */
public class CompanyFacade extends UserFacade implements CompanyDao {

	// attribute
	private UserBase company;

	/**
	 * the constructor get the company name and password and check if they are like in the DB.</br>
	 * if all is correct the method will create object of company and sets the attribute to this company object.</br>
	 * if the name or the password in incorrect it throw a ConnectingUserDBException.
	 * @param company_name String.
	 * @param password String.
	 * @throws ConnectingUserDBException
	 */
	public CompanyFacade(String company_name, String password) throws ConnectingUserDBException {
		if(!login(company_name, password)){
			throw new ConnectingUserDBException("the company or the password dose not exist");
		}
		company = getUserByName(company_name, UserType.COMPANY);
	}

	/**
	 * the method get a {@link Coupon} object.</br>
	 * the method check if the coupon <tt>Title</tt> that the company create have already coupon with the same title in the DB.</br> 
	 * if the title of the coupon is not in the DB so the method will create the coupon in the DB</br>
	 * and insert the coupon to the id`s of the coupon and company to the JOIN table. 
	 * 
	 */
	@Override
	public void createCoupon(Coupon coupon) throws CouponTitleException {
		UserDBDao userDBDao = getUserDBDao(getUserType());
		Coupon couponDb = userDBDao.getCouponByTitle(coupon.getTitle());
		if(couponDb.getTitle() == null){
			((CompanyDBDao) userDBDao).createCoupon(coupon);
			userDBDao.insertToJoinUserCouponTable(company, coupon);
		}else if(couponDb.getTitle().equals(coupon.getTitle())){
			throw new CouponTitleException("the coupon title '" + coupon.getTitle() + "' allredy exist");
		}
		else{
			throw new CouponTitleException("the coupon title '" + coupon.getTitle() + "' allredy exist");
		}
	}

	/**
	 * the method will remove the coupon from all of the JOINs tables first,</br>
	 * after the coupons has been remove from all of the joins tables it will be removed from the coupon table.
	 */
	@Override
	public void removeCoupon(Coupon coupon) {
		UserDBDao userDBDao = getUserDBDao(getUserType());
		userDBDao.removeFromJoinUserCouponTable(coupon);
		((CompanyDBDao) userDBDao).removeCoupon(coupon);
	}

	/**
	 * the method upDate the coupon parameters in the DB.
	 */
	@Override
	public void upDateCoupon(Coupon coupon) {
		UserDBDao userDBDao = getUserDBDao(getUserType());
		((CompanyDBDao) userDBDao).upDateCoupon(coupon);
		userDBDao.insertToJoinUserCouponTable(company, coupon);
	}

	/**
	 * this method is a service for the user.</br>
	 * this method will return a Collection of <tt>Coupons</tt> object with all of the Coupons that the user have in the DB.</br>
	 * it search in the JOIN tables.</br>
	 * the method know witch table to search for by the {@link UserType} that the facade will send.</br>
	 * <strong>NOTE:</strong> the returned collection is unmodifiableCollection.
	 * @return Collection of all the user Coupons from the DB.
	 */
	public Collection<Coupon> getAllUserCoupons() {
		Collection<Coupon> coupons = getUserDBDao(this.company).getAllUserCoupons(this.company);
		return coupons;
	}

	/**
	 * return the {@link UserType#COMPANY}
	 */
	@Override
	public UserType getUserType() {
		return UserType.COMPANY;
	}

	/**
	 * return the attribute of the user that login to the system in the constructor.
	 */
	@Override
	public UserBase getUser() {
		return this.company;
	}


}
