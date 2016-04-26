package com.ee.Thread;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import com.ee.Base.Coupon;
import com.ee.DBDao.CompanyDBDao;
import com.ee.Exception.CouponExpireThreadInterrupedException;

public class CouponExpire extends Thread {
	private static final long threadSleep = TimeUnit.MINUTES.toMillis(5);
	private static int year = LocalDate.now().getYear();
	private static int month = LocalDate.now().getMonthValue();
	private static int day = LocalDate.now().getDayOfMonth();
	private static LocalDate expiredCouponDate = LocalDate.of(year, month, day).minusDays(1);
	private static boolean stop = false;

	/**
	 * this thread is calling to the method {@link #couponValid()}
	 * if coupon have been removed it will  print to the console when it occur.
	 * if no coupon have been removed it will print to the console that there is not coupons to be removed.
	 * then the thread go to sleep for 1 day.
	 * 
	 */
	@Override
	public void run() {

		while(!stop){
			if(!couponValid()){
				System.out.println("coupon removed at: " + LocalDateTime.now());
			}
			else{
				System.out.println("there is no coupon to be removed");
			}
			System.out.println("thread sleep");
			try {
				Thread.sleep(threadSleep);
			} catch (InterruptedException e) {
				throw new CouponExpireThreadInterrupedException("An error acurre while the thread is sleeping");
			}
			System.out.println("thread wake\n");
		}
		System.out.println("thread is killed");
	}


	/**
	 * the method uses to the class {@link CompanyDBDao}</br>
	 * then its call to the method {@link CompanyDBDao#getCouponsByMaxEndDate(LocalDate)} from the class CompanyDBDao,</br>
	 * this method return a Collection with all of the coupons that the end date of then is a day minus today.</br>
	 * with a <tt>For Each</tt> loop the method will remove the coupons one by one.</br></br>
	 * 
	 * <strong>NOTE:</strong> the connection to the DB is created by the methods in the CompanyDBDao.
	 * 
	 * @return <Strong>True</strong> if the collection of the coupons is after delete and empty.</br>
	 *  <strong>False</strong> if nothing happens.
	 * 
	 */
	private boolean couponValid() {
		CompanyDBDao db = new CompanyDBDao();
		Collection<Coupon> coup = db.getCouponsByMaxEndDate(expiredCouponDate);
		for(Coupon e : coup){
			db.removeFromJoinUserCouponTable(e);
			db.removeCoupon(e);
			System.out.println("coupon that has been removed:\n" + e);
		}
		if(coup.isEmpty()){
			return true;
		}
		return false;
	}

	/**
	 * this will make the thread to stop.
	 */
	public void killCouponExpiredThread(){
		System.out.println("Kill thread called");
		stop = true;
		this.getPriority();
		this.interrupt();
	}

}
