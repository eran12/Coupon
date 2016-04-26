package com.ee.Base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.validator.routines.EmailValidator;

import com.ee.Exception.BaseException;
import com.ee.Exception.IllegalPasswordException;
import com.ee.Exception.IllegalUserEmail;
import com.ee.Exception.IllegalUserIdException;
import com.ee.Exception.IllegalUserName;

/**
 * this class is abstract because it holds all the logic of all system users types.</br>
 * the class has getters and setters of the Name, Password, Email, id and a Collection of Coupons.</br>
 * the {@link UserType} is in the Constructor and the class the Extends it must give the UserType that all the
 * Dao layer.</br>
 * all of the attributes are <tt>protected</tt> so it will be available to all of the <tt>com.ee.Base Package</tt>.</br>
 * 
 * @author Yossef Eran Eichenbaum
 *
 */
@XmlRootElement
public abstract class UserBase implements Comparable<UserBase>{

	// Attributes 
	protected String name;
	protected String password;
	protected String email;
	protected long id;
	protected final UserType userType;
	protected Collection<Coupon> coupon;
	
	
	/**
	 * the constructor must get a {@link UserType} so that class Extends it will tell 
	 * witch kind of type to check at the <tt>com.ee.Dao Package</tt>.</br>
	 * the given <tt>String</tt> in the <tt>UserType</> will be also the name of the table inside the DB.
	 * @param userType {@link UserType}
	 */
	public UserBase(UserType userType) {
		super();
		this.userType = userType;
		this.coupon = new ArrayList<>();
	}
	/**
	 * this method is abstract because we want to know how is calling this class.</br>
	 * the class that calling should get the table name from the {@link UserType} <tt>ENUM</tt> class.</br>
	 * 
	 * The <tt>ENUM</tt> type is also  the name of the Table in the DB.</br>
	 * or in the schema in <tt>XML</tt> files or any other kind of protocol
	 */
	public abstract String getUserTableName();
	
	/**
	 * the method calls to the {@link #getUserTableName()} and add the <tt>JOIN</tt> table name of the coupons 
	 * and the user type from the {@link UserType} class.
	 * @return String user table name (as it written in the UserType String) + "_coupon".
	 * 
	 * @see {@link UserType}
	 */
	public String getCouponTableName() {
		return getUserTableName() + "_coupon";
	}
	/**
	 * this constructor sends the parameters (UserType userType, String userName, String password, String email)
	 * to the setters.
	 * the setters check if the data is valid.
	 * if some thing is not valid it throw a <tt>BaseExcption</tt>.
	 *  
	 * @param userName String.
	 * @param password String.
	 * @param email String.
	 * @throws BaseException
	 * 
	 * @see {@link #setUserName(String)}
	 * @see {@link #setPassword(String)}
	 * @see {@link #setEmail(String)}
	 */
	public UserBase(UserType userType, String userName, String password, String email) throws BaseException {
		this(userType);
		setUserName(userName);
		setPassword(password);
		setEmail(email);
	}

	/**
	 * @return <tt>String</tt> of the user E-mail.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * this sets the email and validate the data with the 
	 * <tt>apache.commons.validator.routines.EmailValidator</tt>.</br>
	 * 
	 * @param email String - should contain '@' and '.' inside the string.
	 * @throws IllegalUserEmail if the data is not valid.
	 */
	public void setEmail(String email) throws IllegalUserEmail {
		if(!EmailValidator.getInstance().isValid(email)){
			throw new IllegalUserEmail("the mail is not valid " + email);
		}
		this.email = email;
	}

	/**
	 * @return <tt>String</tt> of the user name.
	 */
	public String getUserName() {
		return name;
	}

	/**
	 * this sets the user name and validate the data. </br>
	 * the user name can not be <tt>Null</tt> or <tt>empty</tt>.</br>
	 * the method check after trimming the String and for the length that is bigger then 0.</br>
	 * and not an empty String.
	 * 
	 * 
	 * @param userName String.
	 * @throws BaseException if the data is not valid.
	 */
	public void setUserName(String userName) throws IllegalUserName {
		if(userName.trim().length() <= 0 || userName.trim().equals("") || userName == null){
			throw new IllegalUserName("the userName name can`t be " + userName);
		}
	
		this.name = userName;
	}

	/**
	 * @return <tt>String</tt> with user password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * this sets the user password and validate the data. </br>
	 * the password can not be <tt>Null</tt> or <tt>empty</tt>.</br>
	 * the method check after trimming the String and for the length that is bigger then 0.</br>
	 * and not an empty String.</br>
	 * if it does it throw {@link IllegalPasswordException}.
	 * @param password String  - bigger then 0 words after trimming.
	 */
	public void setPassword(String password) throws IllegalPasswordException {
		if(password.trim().length() <= 0 || password.trim().equals("") || password == null){
			throw new IllegalPasswordException("the password is illegal password: ");
		}
		this.password = password;
	}

	/**
	 * 
	 * @return <tt>long</tt> id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * this method sets the id.</br>
	 * the id should be bigger then 0. 
	 * 
	 * @param id long - the id should be bigger then 0.
	 * @throws BaseException if the id is smaller then 0.
	 */
	public void setId(long id) throws IllegalUserIdException {
		if(id < 0) throw new IllegalUserIdException("id can't be " + id);
		this.id = id;
	}
	
	/**
	 * 
	 * @return the {@link UserType} that give in the constructor
	 */
	public UserType getUserType() {
		return userType;
	}
	
	/**
	 * 
	 * @return {@link Collection} 
	 */
	public Collection<Coupon> getCoupons() {
		return Collections.unmodifiableCollection(coupon);
	}
	
	/**
	 * 
	 * @param coupon
	 */
	public void setCoupons(Coupon coupon) {
		this.coupon.add(coupon);
	}
	
	/**
	 * this method compares the object by the name.
	 */
	@Override
	public int compareTo(UserBase o) {
		
		return this.name.compareTo(o.name);
	}
	/***
	 * @return the userName + Password + Email.
	 */
	@Override
	public String toString() {
		return "NAME: " + getUserName() + " , password: " + getPassword() + ", E-MAIL: " + getEmail() + "\n" ;
	}
	
}
