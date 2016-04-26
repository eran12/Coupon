package com.ee.Dao;

import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.Exception.BaseException;

/**
 * this interface holds all of UserTypes functions so that every type of user can do in the system.</br>
 * it extends the {@link CouponDao} interface because all of the user types can also make all of the function that is in this interface.
 * @author Yossef Eran Eichenbaum.
 */
public interface UserDao extends CouponDao {
	
	/**
	 * the {@link UserType} is to know witch table in the DB to look for the variables.
	 * if the name or password are not the same as in the DB or not found than a {@link BaseException} should be thrown.
	 * 
	 * @param userName String.
	 * @param password String.
	 * @param userType {@link UserType}
	 * @return true if the password and name is like in the DB.</br>
	 * false if it not the same.
	 * @throws BaseException
	 */
	public boolean login(String userName, String password, UserType userType) throws BaseException;
	
	/**
	 * the method should check if the given name, email and old password is exactly like in the DB.</br>
	 * if it is like the DB than it will override the old password in the new password.</br>
	 * if it not like in the DB then a {@link BaseException} should be thrown.
	 * 
	 * @param userName String.
	 * @param email String.
	 * @param oldPassword String.
	 * @param newPassword String.
	 * @throws BaseException
	 */
	public void setUserPassword(UserBase userName, String email, String oldPassword, String newPassword) throws BaseException;
	
	/**
	 * this will update the user details in the DB.</br>
	 * <strong>NOTE:</STRONG> the ID and NAME will not be update as part of the object update.
	 * @param userName {@link UserBase}
	 * @throws BaseException
	 */
	public void upDateUser(UserBase userName) throws BaseException;
	
	/**
	 * the method should bring the {@link UserBase} object by the name of the user.</br>
	 * 
	 * @param userName String.
	 * @return {@link UserBase} by the name.
	 * @throws BaseException
	 */
	public UserBase getUserByName(String userName, UserType userType) throws BaseException;
	
	/**
	 * the method should bring the {@link UserBase} object by the given id and {@link UserType}.</br>
	 * 
	 * @param id long.
	 * @param userType {@link UserType}
	 * @return {@link UserBase} object.
	 * @throws BaseException
	 */
	public UserBase getUserById(long id, UserType userType) throws BaseException;
	
	/**
	 * this method should return the user Email from the DB.
	 * @param user
	 * @return
	 * @throws BaseException
	 */
	public String getUserEmail(UserBase user) throws BaseException;

	/**
	 * this method should set only the email in the DB.</br>
	 * it look for the user in the table by the id.</br>
	 * if the new email is like the old email it will throw a {@link BaseException}.
	 * @param user {@link UserBase}
	 * @param email String.
	 * @throws BaseException
	 */
	public void setUserEmail(UserBase user, String email) throws BaseException;

	/**
	 * the method will search in the table by the {@link UserType} that given.</br>
	 * than it will check if the name and email is exact as in the DB.</br>
	 * if it does it will return a String with the user password.</br>
	 * if not it will throw a {@link BaseException}.
	 * @param userName String.
	 * @param userEmail String.
	 * @param userType {@link UserType}.
	 * @return String with the user password.
	 * @throws BaseException
	 */
	public String getUserPassword(String userName, String userEmail, UserType userType) throws BaseException;
	
}
