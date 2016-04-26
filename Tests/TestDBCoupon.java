package Tests;

import java.sql.SQLException;
import java.time.LocalDate;

import com.ee.Base.Coupon;
import com.ee.Base.CouponType;
import com.ee.DBDao.CompanyDBDao;
import com.ee.Exception.BaseException;

public class TestDBCoupon {
	
	public static void main(String[] args) throws SQLException, BaseException {
		
		

			LocalDate timeStart ;
			LocalDate timeEnd ;
			timeStart = LocalDate.of(2016, 02, 01);
			timeEnd = LocalDate.of(2016, 02, 28);
		
		CompanyDBDao db = new CompanyDBDao();
		
//		db.createCoupon(new Coupon("cccc", timeStart, timeEnd, 400, CouponType.BABY, "25% discount on all of the chears", 600));
//		db.removeCoupon(db.getCouponById(17));
//		c = db.getCouponById(1);
//		c.setAmount(20);
//		db.upDateCoupon(c);
//		System.out.println(db.getCouponsByType(t));
//		System.out.println(db.getAllCoupons());
//		System.out.println(db.getCouponsByPrice(600));
//		System.out.println(db.getCouponsByEndDate(timeEnd));
		System.out.println(db.getCouponsByCompany(5));
		System.out.println("Finish");
	}

}
