package com.ee.Facade;

import java.util.Collection;

import com.ee.Base.Coupon;
import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.DBDao.CustomerDBDao;
import com.ee.DBDao.UserDBDao;
import com.ee.Dao.CustomerDao;
import com.ee.Exception.ConnectingUserDBException;
import com.ee.Exception.CustomerAllReadyPurchesCoupon;

public class CustomerFacade extends UserFacade implements CustomerDao {

	//
	// attribute
	//
	private UserBase customer;

	/**
	 * the constructor get the customer name and password and check if they are like in the DB.</br>
	 * if all is correct the method will create object of customer and sets the attribute to this customer object.</br>
	 * if the name or the password in incorrect it throw a ConnectingUserDBException.
	 * @param customer_name String.
	 * @param password String.
	 * @throws ConnectingUserDBException
	 */
	public CustomerFacade(String customer_name, String password) throws ConnectingUserDBException {
		if(!login(customer_name, password,UserType.CUSTOMER)){

			throw new ConnectingUserDBException("the customer name or the password dose not exist");
		}else{
			customer = getUserByName(customer_name, UserType.CUSTOMER);
		}
	}

	/**
	 * this method is only for customer user.</br>
	 * the method calls to the method {@link CustomerDBDao#purchaseCoupon(Coupon)}</br>
	 * and after that it calls to the method {@link CustomerDBDao#insertToJoinUserCouponTable(UserBase, Coupon)}
	 */
	@Override
	public void purchaseCoupon(Coupon coupon)throws CustomerAllReadyPurchesCoupon {
		UserDBDao userDBDao = getUserDBDao(UserType.CUSTOMER);
		Collection<Coupon> coupons = userDBDao.getAllUserCoupons(customer);
		for(Coupon coup : coupons){
			if(coup.getId() == coupon.getId()){
				throw new CustomerAllReadyPurchesCoupon("the customer already purches this coupon - coupon id: " + coupon.getId() + " coupon title: " + coupon.getTitle());

			}
		}
		((CustomerDBDao)userDBDao).purchaseCoupon(coupon);
		insertToJoinUserCouponTable(customer, coupon);
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
		try{
			//		if(getUserType().equals(UserType.ADMIN)){
			//			throw new AdminRequsetNotAuthoraized("admin can not do this action");
			//		}else{
			Collection<Coupon> coupons = getUserDBDao(this.customer).getAllUserCoupons(this.customer);
			return coupons;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

		//}
	}

	/**
	 * return the {@link UserType#CUSTOMER}
	 */
	@Override
	public UserType getUserType() {
		return UserType.CUSTOMER;
	}

	/**
	 * return the attribute of the user that login to the system in the constructor.
	 */
	@Override
	public UserBase getUser() {
		return this.customer;
	}

}
