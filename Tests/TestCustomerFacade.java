package Tests;

import java.sql.SQLException;

import com.ee.Exception.BaseException;
import com.ee.Facade.CustomerFacade;

public class TestCustomerFacade {
	
	public static void main(String[] args) throws SQLException, BaseException {
		CustomerFacade fc = new CustomerFacade("eran", "eran");
//		System.out.println(fc.getAllCoupons());
//		fc.purchesCoupon(0);
//		System.out.println(fc.getCouponsByType(CouponType.FOOD));
//		System.out.println(fc.getAllCustomerCoupons());
//		
//		System.out.println(fc.toString());
//		fc.setCustomerEmail("eran@e.com");
//		fc.setUserPassword(fc.getUserByName("eran"), "e@e.com", "eran", "eran");
//		System.out.println("email:" + fc.getUserEmail(customer));
//        System.out.println("password: " +fc.getUserPassword("eran", "e@e.com"));		
//		System.out.println(fc.getUserEmail());
		fc.purchaseCoupon(fc.getCouponById(135));
		System.out.println(fc.getAllCoupons());
		
		System.out.println("finish");
	}

}
