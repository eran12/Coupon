package com.ee.Dao;

import java.util.Collection;

import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.Exception.BaseException;

/**
 * this class is extends the {@link UserDao} class.</br>
 * so that admin will have all of the user functions plus his own functions. 
 * @author Yossef Eran Eichenbaum.
 *
 */
public interface AdminDao extends UserDao {

	/**
	 * this method should create a user object in the DB.
	 * @param user {@link UserBase}
	 */
	public void createUser(UserBase user) throws BaseException;

	/**
	 * this method should remove a user object from the DB.</br>
	 * @param user {@link UserBase}
	 * @throws BaseException
	 */
	public void removeUser(UserBase user) throws BaseException;
	
	/**
	 * this method should remove a user object from the DB by the {@link UserType} and id.</br>
	 * @param userType {@link UserType}
	 * @param userId long.
	 * @throws BaseException
	 */
	public void removeUserById(UserType userType, long userId) throws BaseException;
	
	/**
	 * this method should return a collection with all the users that in the DB by the {@link UserType}.
	 * @param userType {@link UserType}
	 * @return Collection with all the users by the given {@link UserType}.
	 * @throws BaseException
	 */
	public Collection<UserBase> getAllUsers(UserType userType) throws BaseException;

}
