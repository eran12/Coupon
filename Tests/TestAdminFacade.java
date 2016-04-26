package Tests;

import com.ee.Base.Customer;
import com.ee.Base.UserType;
import com.ee.Exception.BaseException;
import com.ee.Facade.AdminFacade;

public class TestAdminFacade {
	
	public static void main(String[] args)  {
		try{
//		CouponExpire ex = new CouponExpire();
//		Thread t = new Thread(ex);
//		t.start();
    	AdminFacade fc = new AdminFacade("eran", "eran");
//		System.out.println(fc.getCouponsByEndDate(LocalDate.of(2017, 01, 01)));
		//System.out.println(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth()).minusDays(1));
		//fc.loginAsCompany("or", "or");
		//fc.removeCoupon(fc.getCouponById(10));
		//Coupon c = fc.getCouponById(0);
//		adminConnect();
//		fc.createCompany(new Company("bbbb", "e@e.com", "password"));
//		fc.createUser(new Company("e", "e@e.com", "e"));
    	fc.createUser(new Customer("eran", "eran", "e@e.com"));
//		System.out.println(fc.getAllUsers(UserType.CUSTOMER));
//		System.out.println(fc.getUserPassword("bla", "bla@bla.org.il"));
//		//Coupon cop = new Coupon("test", LocalDate.of(2016, 01, 01) , LocalDate.of(2016, 05, 31), 50, CouponType.BABY, "message", 7.8);
//		System.out.println(fc.getCompanyCoupons(comp));
//    	fc.setUserPassword("e@e.com", "era", "eran");
//    	System.out.println(fc.getUserById(200));
//    	System.out.println(fc.getUserPassword("eran", "e@e.com"));
//		System.out.println(fc);
    	System.out.println(fc.getAllUsers(UserType.COMPANY));
		}
		catch(BaseException e){
			System.out.println(e.getMessage());
		}
		
	}

}
