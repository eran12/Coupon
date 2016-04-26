package com.ee.System;

import com.ee.Base.UserType;
import com.ee.Exception.BaseException;
import com.ee.Exception.UnKnownUserTypeException;
import com.ee.Facade.AdminFacade;
import com.ee.Facade.CompanyFacade;
import com.ee.Facade.CustomerFacade;
import com.ee.Facade.UserFacade;
import com.ee.Thread.CouponExpire;

public class CouponSystem {
	
	private static final CouponSystem system  = new CouponSystem();

	/**
	 * the attributes is that the Coupon System can start the {@link CouponExpire} thread.
	 */
	private static final CouponExpire ex = new CouponExpire();

	/**
	 * the constructor calls the method {@link #startCouponExpire()}
	 */
	private CouponSystem() {
		startCouponExpire();
	}

	/**
	 * the method will start the {@link CouponExpire} thread.
	 * the thread calls the start() method and not to the run().
	 */
	private void startCouponExpire() {
		Thread thread = new Thread(ex);
		thread.start();
	}

	public static CouponSystem getInstance(){
		return CouponSystem.system;
	}
	
	/**
	 * the method check all cases if the {@link UserType}.</br>
	 * then if the user type is found it calls to the methods:</br>
	 * {@link #returnAdminFacade(String, String)}</br>
	 * {@link #returnCompanyFacade(String, String)}</br>
	 * {@link #returnCustomerFacade(String, String)}</br>
	 * 
	 * @param String name
	 * @param String password
	 * @param UserType type
	 */
	public UserFacade login(String name, String password, UserType userType) throws UnKnownUserTypeException {
		UserFacade fc;
		
		switch(userType){
		case CUSTOMER:
			fc = returnCustomerFacade(name, password);
			break;
		case COMPANY:
			fc = returnCompanyFacade(name, password);
			break;
		case ADMIN:
			fc = returnAdminFacade(name, password);
			break;
		default:
			throw new UnKnownUserTypeException("unknown user type " + userType);
		}
		return fc;
	}

	/**
	 * the method gets a String name, and String password.</br>
	 * its sends it to the {@link AdminFacade} and check the login.</br>
	 * if the name or password is incorrect it throws a {@link BaseException}.</br>
	 * 
	 * @param String name
	 * @param String password
	 * @return an object from type {@link AdminFacade}.
	 */
	private AdminFacade returnAdminFacade(String name,String password) {
		AdminFacade admin = new AdminFacade(name, password);
		return admin;
	}
	
	/**
	 * the method gets a String name, and String password.</br>
	 * its sends it to the {@link CompanyFacade} and check the login.</br>
	 * if the name or password is incorrect it throws a {@link BaseException}.</br>
	 * 
	 * @param String name
	 * @param String password
	 * @return an object from type {@link CompanyFacade}.
	 */
	private CompanyFacade returnCompanyFacade(String name, String password){
		CompanyFacade comp = new CompanyFacade(name, password);
		return comp;
	}

	/**
	 * the method gets a String name, and String password.</br>
	 * its sends it to the {@link CustomerFacade} and check the login.</br>
	 * if the name or password is incorrect it throws a {@link BaseException}.</br>
	 * 
	 * @param String name
	 * @param String password
	 * @return an object from type {@link CustomerFacade}.
	 */
	private CustomerFacade returnCustomerFacade(String name, String password) {
		CustomerFacade cust = new CustomerFacade(name, password);
		return cust;
	}

	/**
	 * the method calls to the method <tt>killCouponExpiredThread()</tt></br>
	 * inside the {@link CouponExpire} class. </br>
	 * the method will destroy the thread and log out the user. 
	 */
	public static void shutDown(){
		ex.killCouponExpiredThread();
		
	}
}
