package Tests;

import java.sql.SQLException;
import java.time.LocalDate;

import com.ee.Base.Coupon;
import com.ee.Base.CouponType;
import com.ee.DBDao.CouponDBDao;
import com.ee.Exception.BaseException;
import com.ee.Facade.CompanyFacade;

public class TestCompanyFacade {
	public static void main(String[] args) throws SQLException, BaseException {
		
		CompanyFacade fc = new CompanyFacade("bl", "newPassword");
//		CouponDBDao c = new CouponDBDao();
		//fc.upDateCompany(name, email, password);
//		fc.setCompanyPassword("b@bb.com","ble", "newPassword");
//		System.out.println(fc.getCompanyEmail());
//		System.out.println(fc.toStringCompany());
//		Coupon co = fc.getCouponById(4);
//		fc.removeCoupon(co);
//		co.setPrice(300);
//		fc.upDateCoupon(co);
//		System.out.println(fc.getCompanyCoupons());
//		fc.getCouponById(1).setCompanyId(8);
//		System.out.println(fc.toStringCompany());
		fc.createCoupon(new Coupon("test200", LocalDate.of(2016, 01, 19), LocalDate.of(2016, 05, 19), 400, CouponType.BABY, "25% discount on all of the chears", 600.0) );
//		System.out.println(fc.getCompanyCoupons());
//		System.out.println(fc.getCouponsByEndDate(timeEnd));
//		System.out.println(fc.getCouponsByStartDate(timeStart));
//		System.out.println(fc.getCouponsByPrice(600));
//		System.out.println(fc.getCompanyCoupons());
//		System.out.println(fc);
//		fc.removeCoupon(fc.getCouponById(4));
		System.out.println(fc.getAllUserCoupons());
		
//		fc.removeCoupon(fc.getCouponById(1));
	}
	

}
