package Tests;

import java.sql.SQLException;
import java.time.LocalDate;

import com.ee.Base.Coupon;
import com.ee.Base.CouponType;
import com.ee.Exception.BaseException;
import com.ee.Facade.CompanyFacade;
import com.ee.Thread.CouponExpire;

public class TestThread {
	public static void main(String[] args) throws ClassNotFoundException, BaseException, SQLException {
		CompanyFacade f = new CompanyFacade("bla", "bla");
		f.createCoupon(new Coupon("test55", LocalDate.of(2015, 12, 12),LocalDate.of(2015, 12, 31), 10, CouponType.FOOD," message", 19.9));
		
		CouponExpire ex = new CouponExpire();
		Thread t = new Thread(ex);
		t.start();
		System.out.println("finish");
	}
}
