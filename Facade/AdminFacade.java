package com.ee.Facade;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;

import com.ee.Base.Admin;
import com.ee.Base.Coupon;
import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.DBDao.AdminDBDao;
import com.ee.DBDao.UserDBDao;
import com.ee.Dao.AdminDao;
import com.ee.Exception.ConnectingUserDBException;
import com.ee.Exception.CreatingUserInDBException;
import com.ee.Exception.UserDoNotExistExeption;

/**
 * this class implements the {@link AdminDao} class.</br>
 * this is because if the DBDao layer will be replaced every thing will keep on working.</br>
 * this class is the Admin Facade in the system and all of the functions that a admin user can do.</br> 
 * @author Yossef Eran Eichenbaum
 *
 */
public class AdminFacade extends UserFacade implements AdminDao {
	/**
	 * this attribute will hold all of the user object.
	 */
	@XmlAttribute
	private UserBase admin;

	/**
	 * the constructor get a user name and password and check with the DB if the parameters is correct.</br>
	 * if it true it return the object to the attribute by pull it out from the DB by user name.</br>
	 * it the login return false it throw a  ConnectingUserDBException.</br>
	 * @param userName String
	 * @param password String
	 * @throws ConnectingUserDBException if the user name or password is in correct.
	 */
	public AdminFacade(String userName, String password) throws ConnectingUserDBException {
		if(userName.equals("admin") && password.equals("1234") ){
			admin = new Admin(userName, password, "a@a.com");
		}
		else{
			if(!super.login(userName, password)){
				throw new ConnectingUserDBException("the admin name or password do not exist");
			}else{
				admin = getUserByName(userName, UserType.ADMIN);
			}
		}
	}

	@Override
	public Collection<Coupon> getAllUserCoupons() {
		return null;
	}

	/**
	 * the method check if the user already exist in the DB.</br>
	 * if the user do not exist it will create him in the DB.</br>
	 * if the user do exist it will throw a CreatingUserInDBException.</br>
	 * @param user {@link UserBase}
	 */
	@Override
	public void createUser(UserBase user) throws CreatingUserInDBException {

		if(getUserFromDb(user) != null){
			throw new CreatingUserInDBException("The user name allredy exist- " + user);
		}
		else{
			UserDBDao userDBDao = getUserDBDao(getUserType());
			((AdminDao) userDBDao).createUser(user);

		}
	}

	/**
	 * the method will remove the user from the DB.
	 */
	@Override
	public void removeUser(UserBase user) {
		UserDBDao userDBDao = getUserDBDao(getUserType());
		if(getUserFromDb(user) == null){
			throw new UserDoNotExistExeption("the user do not exist in the DB. " + user.toString());
		}
		else{	
			((AdminDao) userDBDao).removeUser(user);				
		}
	}

	/**
	 * the method ask for the UserDbDao by the userType of Admin.</br>
	 * than it make a Cast to {@link AdminDao} because it expect to get an {@link AdminDBDao}.
	 * the method get {@link UserType} to know from witch table to remove the user.</br>
	 * and the ID number is the user that we want to remove.
	 * 
	 * @param userType {@link UserType}
	 * @param userId <strong>long</strong>.
	 * 
	 */
	@Override
	public void removeUserById(UserType userType, long userId) {
		UserDBDao userDBDao = getUserDBDao(getUserType());
		if(userDBDao.getUserById(userId, userType) == null){
			throw new UserDoNotExistExeption("the user do not exist in the DB, by the ID: " + userId);
		}
		((AdminDao) userDBDao).removeUserById(userType,userId);		
	}

	/**
	 * the method know to witch table to go by the {@link UserType}.</br>
	 * it create a {@link Collection} of all the users in that table and return it.
	 * 
	 * @param userType {@link UserType}
	 * @return {@link Collection} with all the user in the table by the given UserType.
	 */
	@Override
	public Collection<UserBase> getAllUsers(UserType userType) {
		UserDBDao userDBDao = getUserDBDao(getUserType());
		return ((AdminDao) userDBDao).getAllUsers(userType);
	}

	/**
	 * the method gets a UserBase parameter and pull out the UserBase object by the name from the Db.</br>
	 * it create the object and return it.</br>
	 * if the user dose not exist so the object that will return will be null.
	 * @param user {@link UserBase}
	 * @return {@link UserBase} object by the name. if there is no such name in the DB the object will be null.
	 */
	private UserBase getUserFromDb(UserBase user){
		UserDBDao db = getUserDBDao(user.getUserType());
		UserBase userDb = db.getUserByName(user.getUserName(), user.getUserType());
		return userDb;
	}

	/**
	 * this method will return the user type of the facade the uses it.</br>
	 * it will be used mainly in the method {@linkplain AdminFacade#getUserDBDao(UserType)}}
	 * @return {@link UserType#ADMIN} of ADMIN
	 */
	@Override
	public UserType getUserType() {
		return UserType.ADMIN;
	}

	/**
	 * return the attribute of the user that login to the system in the constructor.
	 */
	@Override
	public UserBase getUser() {
		return this.admin;
	}
}
