package com.ee.Facade;

import java.time.LocalDate;
import java.util.Collection;

import com.ee.Base.Coupon;
import com.ee.Base.CouponType;
import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.DBDao.AdminDBDao;
import com.ee.DBDao.CompanyDBDao;
import com.ee.DBDao.CouponDBDao;
import com.ee.DBDao.CustomerDBDao;
import com.ee.DBDao.UserDBDao;
import com.ee.Dao.UserDao;
import com.ee.Exception.BaseException;
import com.ee.Exception.UnKnownUserTypeException;
import com.ee.Exception.UserDoNotExistExeption;

/**
 * this class implements the {@link UserDao} class.</br>
 * this is because if the DBDao layer will be replaced every thing will keep on working.</br>
 * this is the basic class in the Facade layer so all the users needs to extends it.</br> 
 * @author Yossef Eran Eichenbaum
 *
 */
public abstract class UserFacade implements UserDao {

	/**
	 * this is abstract because the class that <tt>Extends</tt> will tell how he is.</br>
	 * this is a service for couple of methods. 
	 * @return {@link UserType} of the facade class that do the extends.
	 */
	public abstract UserType getUserType();

	/**
	 * this method ask for the user attribute from the facade that implements this class.</br>
	 * @return {@link UserBase} of the facade that implements this class.
	 */
	public abstract UserBase getUser();

	public abstract Collection<Coupon> getAllUserCoupons();

	/**
	 * the method will call to {@link #getUserDBDao(UserType)} and ask for the right userDao.
	 * @param  user {@link UserBase}
	 * @return an object from the UserDBDao layer by the {@link UserType} that the class how called will provide.
	 */
	protected UserDBDao getUserDBDao(UserBase user) {
		return getUserDBDao(user.getUserType());
	}

	/**
	 * the method create and return an instance of UserDBDao layer by the given {@link UserType} that the class how called will provide.
	 * @param userType {@link UserType}
	 * @return UserDBDao 
	 */
	protected UserDBDao getUserDBDao(UserType userType) throws UnKnownUserTypeException {
		UserDBDao db;

		switch (userType) {
		case ADMIN:
			db = new AdminDBDao();
			break;
		case COMPANY:
			db = new CompanyDBDao();
			break;
		case CUSTOMER:
			db = new CustomerDBDao();
			break;
		default:
			throw new UnKnownUserTypeException("unknown user type " + userType);
		}
		return db;
	}

	/**
	 * the method get a {@link UserBase} argument to update the details in the DB.</br>
	 * it create an {@link UserDBDao} instance for the user by the Type of the user.</br>
	 * than it send it to the UserDBDAO layer for implementing it to the DB.
	 */
	@Override
	public void upDateUser(UserBase user){
		UserDBDao db = getUserDBDao(getUser());
		db.upDateUser(user);
	}

	/**
	 * this method is a service for the user.</br>
	 * it gets the user attribute from the facade and update all the object in the DB.
	 */
	public void upDateUser(){
		UserDBDao db = getUserDBDao(getUserType());
		db.upDateUser(getUser());
	}

	/**
	 * the method get a {@link UserBase} argument to pull out the details from the DB.</br>
	 * it create an {@link UserDBDao} instance for the user by the Type of the user.</br>
	 * than it send it to the DBDAO layer to pull out the Email from the DB.
	 * @return String with the user E-mail.</br>
	 * 
	 */
	@Override
	public String getUserEmail(UserBase user){

		UserDBDao db = getUserDBDao(getUser());
		String email = db.getUserEmail(user);
		return email; 
	}

	/**
	 * this method is a service for the user.</br>
	 * it gets the user attribute from the facade and pull out the user email as it in the DB.
	 * @return String with the user Email from the DB.
	 */
	public String getUserEmail(){
		UserDBDao db = getUserDBDao(getUser());
		String email = db.getUserEmail(getUser());
		return email; 
	}

	/**
	 * the method get a String with the user name argument to pull out the details from the DB.</br>
	 * it create an {@link UserDBDao} instance by the method {@link #getUserDBDao(UserType)}.</br>
	 * and create an instance of {@link UserBase} from the DB by the name that given.</br>
	 * than it send the String to the DBDAO layer to pull out the user object from the DB.
	 * 
	 * @param userName <strong>String</strong> 
	 * @return {@link UserBase} object by the given name from the DB.
	 */
	@Override
	public UserBase getUserByName(String userName, UserType userType) throws UserDoNotExistExeption {
		UserDBDao db = getUserDBDao(getUserType());
		UserBase user = db.getUserByName(userName, userType);
		if(user == null){
			throw new UserDoNotExistExeption("The user not found in the DB. user name: " + userName);
		}
		else{
			return user;
		}
	}

	/**
	 * this method sets the user Email directly in the DB.</br>
	 * 
	 */
	@Override
	public void setUserEmail(UserBase user, String userEmail){
		getUserDBDao(getUser()).setUserEmail(user, userEmail);
	}

	/**
	 * this method is a service to the user.</br>
	 * it gets the user attribute from the facade and gets only the user Email as a parameter.
	 * @param userEmail String
	 */
	public void setUserEmail(String userEmail){
		getUserDBDao(getUser()).setUserEmail(getUser(), userEmail);
	}

	/**
	 * this method will return a Collection of <tt>Coupons</tt> object with all of the Coupons that are in the DB.</br>
	 * <strong>NOTE:</strong> the returned collection is unmodifiableCollection.
	 * @return Collection of all the Coupons from the DB.
	 */
	@Override
	public Collection<Coupon> getAllCoupons(){
		Collection<Coupon> coupons = getUserDBDao(getUser()).getAllCoupons();
		return coupons;
	}

	/**
	 * this method will return a <tt>Coupon</tt> object by the given id from the DB.</br>
	 * @return Coupon from the DB by the given id.
	 */
	@Override
	public Coupon getCouponById(long couponId){
		Coupon coupon = getUserDBDao(getUser()).getCouponById(couponId);
		return coupon;
	}

	/**
	 * this method will return a Collection of <tt>Coupons</tt> object with all of the Coupons that the user have in the DB.</br>
	 * it search in the JOIN tables.</br>
	 * the method know witch table to search for by the {@link UserType} that the facade will send.</br>
	 * <strong>NOTE:</strong> the returned collection is unmodifiableCollection.
	 * @return Collection of all the user Coupons from the DB.
	 */
	@Override
	public Collection<Coupon> getAllUserCoupons(UserBase user) {
		Collection<Coupon> coupons = getUserDBDao(getUserType()).getAllUserCoupons(user);
		return coupons;
	}



	/**
	 * this method will return a Collection of <tt>Coupons</tt> object with all of the Coupons that the company have in the DB.</br>
	 * it search in the JOIN tables.</br>
	 * the method know witch table to search for by the company id that the user will send.</br>
	 * <strong>NOTE:</strong> the returned collection is unmodifiableCollection.
	 * @return Collection of all the company Coupons from the DB by the given id.
	 */
	@Override
	public Collection<Coupon> getCouponsByCompany(long id) {
		Collection<Coupon> coupons = getUserDBDao(getUserType()).getCouponsByCompany(id);
		return coupons;
	}

	/**
	 * this method will return a Collection of <tt>Coupons</tt> object with all of the Coupons that the start date is the max date in the DB.</br>
	 * <strong>NOTE:</strong> the returned collection is unmodifiableCollection.
	 * @return Collection of all the user Coupons from the DB.
	 */
	@Override
	public Collection<Coupon> getCouponsByMinStartDate(LocalDate minStartDate) {
		Collection<Coupon> coupons = getUserDBDao(getUserType()).getCouponsByMinStartDate(minStartDate);
		return coupons;
	}

	/**
	 * this method will return a Collection of <tt>Coupons</tt> object with all of the Coupons that the end date is the max date in the DB.</br>
	 * <strong>NOTE:</strong> the returned collection is unmodifiableCollection.
	 * @return Collection of all the user Coupons from the DB.
	 */
	@Override
	public Collection<Coupon> getCouponsByMaxEndDate(LocalDate maxEndDate) {
		Collection<Coupon> coupons = getUserDBDao(getUserType()).getCouponsByMaxEndDate(maxEndDate);
		return coupons;
	}

	/**
	 * this method will return a Collection of <tt>Coupons</tt> object with all of the Coupons that the price is the max price in the DB.</br>
	 * <strong>NOTE:</strong> the returned collection is unmodifiableCollection.
	 * @return Collection of all the user Coupons from the DB.
	 */
	@Override
	public Collection<Coupon> getCouponsByMaxPrice(double price) {
		Collection<Coupon> coupons = getUserDBDao(getUserType()).getCouponsByMaxPrice(price);
		return coupons;
	}

	/**
	 * this method will return a Collection of <tt>Coupons</tt> object with all of the Coupons
	 * that have the same {@link CouponType} in the DB.</br>
	 * <strong>NOTE:</strong> the returned collection is unmodifiableCollection.
	 * @return Collection of all the user Coupons from the DB.
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType type) {
		Collection<Coupon> coupons = getUserDBDao(getUserType()).getCouponsByType(type);
		return coupons;
	}

	/**
	 * the method get a string and search in the DB for the Coupon with the same title.</br>
	 * if there is a coupon with the same title it return the coupon object from the DB.</br>
	 * if there is no such Coupon with the same title as the given string it will return null.
	 */
	@Override
	public Coupon getCouponByTitle(String couponTitle) {
		Coupon coupon = getUserDBDao(getUser()).getCouponByTitle(couponTitle);
		return coupon;
	}

	@Override
	public Collection<Coupon> getCouponsLikeTitle(String couponTitle) throws BaseException {
		Collection<Coupon> coupons = getUserDBDao(getUserType()).getCouponsLikeTitle(couponTitle);
		return coupons;
	}

	/**
	 * this method will insert the user and coupon id to the JOIN table.</br>
	 * the method know to with table put the id`s by the {@link UserType} from the user object.
	 */
	@Override
	public void insertToJoinUserCouponTable(UserBase user, Coupon coupon) {
		getUserDBDao(getUserType()).insertToJoinUserCouponTable(user, coupon);;		
	}

	/**
	 * this method is a service to the user.</br>
	 * this method will insert the user and coupon id to the JOIN table.</br>
	 * the method know to with table put the id`s by the {@link UserType} from the user object.
	 */
	public void insertToJoinUserCouponTable(Coupon coupon) {
		getUserDBDao(getUserType()).insertToJoinUserCouponTable(getUser(), coupon);;		
	}

	/**
	 * this method will return a <tt>boolean</tt> expression.</br>
	 * the method know witch table to search for the user by the {@link UserType}.</br>
	 * if the user name and password is correct it will return true.</br>
	 * if the user name or password is incorrect it will return false.</br></br>
	 * <strong>NOTE:</strong> the method is case sensitive with the user name and password.
	 */
	@Override
	public boolean login(String userName, String password, UserType userType) {
		boolean login = getUserDBDao(getUserType()).login(userName, password, userType);
		return login;
	}

	/**
	 * this method is service for the user.</br>
	 * the method gets the UserType from the facade that implements this class.</br> 
	 * this method will return a <tt>boolean</tt> expression.</br>
	 * the method know witch table to search for the user by the {@link UserType}.</br>
	 * if the user name and password is correct it will return true.</br>
	 * if the user name or password is incorrect it will return false.</br></br>
	 * <strong>NOTE:</strong> the method is case sensitive with the user name and password.
	 * @return true if the user name and password is correct.</br>
	 * false if the user name or password is incorrect.
	 */
	public boolean login(String userName, String userPassword){
		boolean login = login(userName,userPassword,getUserType());
		return login;
	}

	/**
	 * this method sets the user password in the DB.</br>
	 * 
	 */
	@Override
	public void setUserPassword(UserBase userName, String email, String oldPassword, String newPassword) {
		getUserDBDao(getUser()).setUserPassword(userName, email, oldPassword, newPassword);
	}

	/**
	 * this method is service for the user.</br>
	 * the method gets the UserType from the facade that implements this class.</br> 
	 * and sets the user password in the DB.</br>
	 * @param email String
	 * @param oldPassword String 
	 * @param newPassword String
	 */
	public void setUserPassword(String email, String oldPassword, String newPassword) {
		getUserDBDao(getUser()).setUserPassword(getUser(), email, oldPassword, newPassword);
	}

	/**
	 * the method know witch user to look for by the {@link UserType}.</br>
	 * if the user do not exist it throw UserDoNotExistExeption.</br>
	 */
	@Override
	public UserBase getUserById(long userId, UserType userType) throws UserDoNotExistExeption{
		UserBase user = getUserDBDao(getUser()).getUserById(userId,userType);
		if(user == null){
			throw new UserDoNotExistExeption("the user not found in the DB by the id: " + userId);
		}
		return user;
	}

	/**
	 * the method know witch user to look for by the {@link UserType}.</br>
	 * if the user do not exist it throw UserDoNotExistExeption.</br>
	 * 
	 * @param userId long
	 * @return {@link UserBase} object.
	 */
	public UserBase getUserById(long userId) throws UserDoNotExistExeption {
		UserBase user = getUserDBDao(getUserType()).getUserById(userId,getUserType());
		if(user == null){
			throw new UserDoNotExistExeption("the user not found in the DB by the id: " + userId);
		}
		else{
			return user;
		}
	}

	/**
	 * this method get the user password from the DB.</br>
	 * the method use the {@link UserType} to know witch table to search for the user.</br>
	 */
	@Override
	public String getUserPassword(String userName, String userEmail, UserType userType) {
		String pass = getUserDBDao(getUser()).getUserPassword(userName, userEmail, userType);
		return pass;
	}

	/**
	 * this method is a service for the user.</br>
	 * the method gets the {@link UserType} from the facade that implements this class.</br></br>
	 * it will return the password of the user if the name and email is correct.</br>
	 * if it is not correct it will throw a UserDoNotExistExeption.
	 * <strong> NOTE:</strong> the name and Email is case sensitive.
	 * @param userName String
	 * @param userEmail String
	 * @return String with the user password from the DB.
	 * @throws UserDoNotExistExeption
	 */
	public String getUserPassword(String userName, String userEmail) throws UserDoNotExistExeption {
		String pass = getUserPassword(userName,userEmail,getUserType());
		if(pass == null){
			throw new UserDoNotExistExeption("The user not found in the DB. user name: " + userName + " user email: " + userEmail);
		}
		else{
			return pass;
		}
	}

	/**
	 * this method will remove the Coupon from all of the users JOIN tables where the Coupon id is.</br>
	 * @see {@link CouponDBDao#removeFromJoinUserCouponTable(Coupon)}
	 * 
	 */
	public void removeFromJoinUserCouponTable(Coupon coupon) {
		getUserDBDao(getUser()).removeFromJoinUserCouponTable(coupon);
	}

	/**
	 * this will return the {@link UserBase} toString.
	 */
	@Override
	public String toString() {
		return getUser().toString();
	}



}
