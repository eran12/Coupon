package com.ee.Base;

import java.time.LocalDate;
import java.util.Date;

import com.ee.Exception.BaseException;
import com.ee.Exception.CouponAmountException;
import com.ee.Exception.CouponEndDateException;
import com.ee.Exception.CouponMessageException;
import com.ee.Exception.CouponPriceException;
import com.ee.Exception.CouponStartDateException;
import com.ee.Exception.CouponTitleException;
import com.ee.Exception.UserIdSmallerThenZeroException;

public class Coupon implements Comparable<Coupon>{

	private long id;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	public Coupon(){
	
	}
	
	/**
	 * this constructor send the data to the setters to check if data is valid.</br>
	 * if it not it will throw a {@link BaseException}.
	 * @param String title
	 * @param LocalDate timeStart
	 * @param LocalDate timeEnd
	 * @param int amount
	 * @param CouponType type
	 * @param String message
	 * @param double price
	 * @throws BaseException
	 */
	public	Coupon(String title, LocalDate timeStart, LocalDate timeEnd, int amount, CouponType type, String message, double price) throws BaseException{
		
		setTitle(title);
		setStartDate(timeStart);
		setEndDate(timeEnd);
		setAmount(amount);
		setType(type);
		setMessage(message);
		setPrice(price);

	}

	/**
	 * the method validate the given id.
	 * that is bigger than 0. 
	 * if not throw an 
	 * 
	 * @param long id
	 * @throws UserIdSmallerThenZeroException if the id is smaller then 0.
	 */
	public void setId(long id) throws UserIdSmallerThenZeroException {
		if(id < 0) throw new UserIdSmallerThenZeroException("id can't be " + id);

		this.id = id;
	}

	/**
	 * this sets the title of the coupon.</br>
	 * the title can not be null or only with space or empty.
	 * if it does it throws a {@link BaseException}.
	 * @param String title
	 * @throws CouponTitleException if the title is empty or null.
	 */
	public void setTitle(String title) throws CouponTitleException {
		if (title.equals(null) || title.trim().length() <= 0  || title.trim().equals("")){
			throw new CouponTitleException("the title can`t be null or empty " + title);
		}
		this.title = title;
	}

	/**
	 * get {@link Date} as parameter and check if it after today and that is not the same as the end date.
	 * @param LocalDate startDate
	 * @throws CouponStartDateException
	 */
	public void setStartDate(LocalDate startDate) throws CouponStartDateException {
		if(startDate.equals(endDate)) {
			throw new CouponStartDateException("the start date must be before the end date " + startDate);
		}
		this.startDate = startDate;
	}

	/**
	 * get {@link Date} as parameter and check if it after today and that is not the same as the start date.
	 * @param LocalDate endDate
	 * @throws CouponEndDateException
	 */
	public void setEndDate(LocalDate endDate) throws CouponEndDateException {
		if(endDate.equals(startDate)){
			throw new CouponEndDateException("the end-date must be after the start date " + endDate);
		}
		this.endDate =   endDate;
	}

	/**
	 * sets the amount of the coupon.</br>
	 * its check that the amount is bigger than -1.
	 * @param int amount
	 * @throws CouponAmountException
	 */
	public void setAmount(int amount) throws CouponAmountException {
		if(amount <= -1 ) throw new CouponAmountException("the amount can`t be below 0 " + amount);

		this.amount = amount;
	}

	/**
	 * 
	 * @param type {@link CouponType}
	 */
	public void setType(CouponType type) {
		this.type = type;
	}

	/**
	 * this sets the message of the coupon.</br>
	 * check if the message is not null or empty.</br>
	 * if it does it throw a BaseException
	 * @param message String
	 * @throws CouponMessageException
	 */
	public void setMessage(String message) throws CouponMessageException {
		if (message.equals(null) || message.trim().equals("") || message.trim().length() <= 0){
			throw new CouponMessageException("ther must be a message in the body " + title);
		}
		this.message =  message;
	}

	/**
	 * sets the price of the coupon.</br>
	 * if the price is lower then 0 it throw a BaseException.
	 * @param double price
	 * @throws CouponPriceException
	 */
	public void setPrice(double price) throws CouponPriceException {
		if(price < 0 ) throw new CouponPriceException("the price must be bigger than 0 " + price);
		this.price = price;
	}

	/**
	 * set the image of the coupon 
	 * @param String image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the coupon <tt>long</tt> id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the coupon <tt>String</tt> title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the coupon <tt>LocalDate</tt> start date.
	 */
	public LocalDate getStartDate() {
		return  startDate;
	}

	/**
	 * @return the coupon <tt>LocalDate</tt> end date.
	 */
	public LocalDate getEndDate() {
		return  endDate;
	}
	
	/**
	 * @return the coupon <tt>int</tt> amount.
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @return the coupon <tt>CouponType</tt> type.
	 */
	public CouponType getType() {
		return type;
	}

	/**
	 * @return the coupon <tt>String</tt> message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the coupon <tt>double</tt> price.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return the coupon <tt>String</tt> image.
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @return String of coupon with the follow structure:</br>
	 * Coupon ID + TITLE + CATAGORY + AMOUNTE + PRICE + START DATE + END DATE + DISCREPTION.
	 */
	@Override
	public String toString() {

		return "Coupon ID:" + getId() + ", TITLE: " + getTitle() + ", CATAGORY: " + getType() + ", \nAMOUNTE: " +
				getAmount() + ", PRICE: " + getPrice() + ", \nSTART DATE: " + getStartDate() + ",\nEND DATE: " + getEndDate()
				+ ", \nDISCREPTION: " + getMessage() + "\n\n";
	}

	@Override
	public int compareTo(Coupon o) {
		return this.title.compareTo(o.title);
	}
}


