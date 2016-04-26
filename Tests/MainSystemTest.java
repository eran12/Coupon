package Tests;

import java.time.LocalDate;
import java.util.logging.Logger;

import com.ee.Base.Company;
import com.ee.Base.Coupon;
import com.ee.Base.CouponType;
import com.ee.Base.Customer;
import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.Exception.BaseException;
import com.ee.Facade.AdminFacade;
import com.ee.Facade.CompanyFacade;
import com.ee.Facade.CustomerFacade;
import com.ee.System.CouponSystem;

/**
 * this class is to test the system and all of its users and methods.
 * 
 * @author Yssef Eran Eichenbaum.
 *
 */
public class MainSystemTest {
	static final CouponSystem c = CouponSystem.getInstance();
	public static final Logger log = Logger.getAnonymousLogger();
	public static void main(String[] args) {
		System.out.println("MainSystemTest.main()");
				LocalDate maxDate = LocalDate.of(2016, 05, 12);
		//this try and catch will catch all of the 
		try{
			//			createCompanys();
			//			createCustomers();
						createCoupons();
			//			consolePrintingCompanyCouponsByDate(maxDate);
			//			purchesCoupons();
			//			consolePrintingCustomerCoupons();
			//			upDateCoupon();
			//			getCouponsByCompany();
			//			getAllCompanys();
			//			getAllCustomers();
			//			removeCoupons();
			//			removeCompanys();
			//			removeCustomers();
//			AdminFacade fc = (AdminFacade) c.login("admin", "1234", UserType.ADMIN);
//			fc.removeUserById(UserType.COMPANY, 2559);

			System.out.println("FINISH");
		}
		catch(Exception e){
			//CouponSystem.shutDown();
			log.log(BaseException.log);
			//e.printStackTrace();
		}
		CouponSystem.shutDown();
	}
	public static void createCompanys(){
		System.out.println("MainSystemTest.createCompanys()");
		System.out.println("******************************************************************************************************");
		System.out.println("******************************************************************************************************");
		AdminFacade fc = (AdminFacade) c.login("admin", "1234", UserType.ADMIN);
		Company company ;
		for (int i = 1; i < 100 ; i++) {
			company = new Company("company test " + i, "test" + i +"@test.com", "comptest" + i);
			fc.createUser(company);
		}
	}

	public static void createCustomers(){
		System.out.println("MainSystemTest.createCustomers()");
		System.out.println("******************************************************************************************************");
		System.out.println("******************************************************************************************************");
		AdminFacade fc = (AdminFacade) c.login("admin", "1234", UserType.ADMIN);
		Customer customer;
		for (int i = 1; i < 100 ; i++) {
			customer = new Customer("customer test " + i, "custtest" + i , "test" + i +"@test.com");
			fc.createUser(customer);
		}
	}

	public static void createCoupons(){
		System.out.println("MainSystemTest.createCoupons()");
		System.out.println("******************************************************************************************************");
		System.out.println("******************************************************************************************************");
		Coupon coupon;
		CompanyFacade fc;
		for (int i = 1; i < 100; i++) {
			int day = i;
			if(i>30 && i<60){
				day = i-29;
			}
			else if(i>=60 && i<90){
				day = i-59;
			}
			else if(i>=90){
				day = i-89;
			}
			int month = i;
			if(i%12 == 0)
				month = 12;
			else{
				month = i%12;
			}

			coupon = new Coupon("title " + i, LocalDate.of(2016, month, day), LocalDate.of(2017, month, day), 100,CouponType.CAMPING , "message " + i, i);
			fc = (CompanyFacade) c.login("company test " + i, "comptest" + i, UserType.COMPANY);
			fc.createCoupon(coupon);
		}
	}

	public static void purchesCoupons(){
		System.out.println("MainSystemTest.purchesCoupons()");
		System.out.println("******************************************************************************************************");
		System.out.println("******************************************************************************************************");
		CustomerFacade fc;
		Coupon coupon = null;
		for (int i = 1; i < 100; i++) {
			fc = (CustomerFacade) c.login("customer test " + i, "custtest" + i, UserType.CUSTOMER);
			coupon = fc.getCouponByTitle("title " + i);
			fc.purchaseCoupon(coupon);
			System.out.println("the Customer: " + fc + "\npurches the coupon:\n" + coupon );
			System.out.println("******************************************************************************************************");
		}
	}

	public static void upDateCoupon(){
		System.out.println("MainSystemTest.upDateCoupon()");
		System.out.println("******************************************************************************************************");
		CompanyFacade fc = null;
		Coupon coupon = null;
		for (int i = 1; i < 100; i++) {
			int day = i;
			if(i>30){
				day = i-30;
				if(i>60){
					day = i-60;
					if(i>90){
						day = i-90;
					}
				}
			}

			fc = (CompanyFacade) c.login("company test " + i, "comptest" + i, UserType.COMPANY);
			coupon = fc.getCouponByTitle("title " + i);
			coupon.setAmount(i + 200);
			coupon.setEndDate(LocalDate.of(2016, 12, day));
			coupon.setStartDate(LocalDate.of(2016, 06, day));
			coupon.setMessage("this message is after coupon update");
			coupon.setType(CouponType.BABY);
			fc.upDateCoupon(coupon);
			System.out.println(fc.getAllUserCoupons());
		}
	}

	public static void consolePrintingCompanyCouponsByDate(LocalDate maxDate){
		System.out.println("MainSystemTest.consolePrintingCompanyCoupons()");
		System.out.println("******************************************************************************************************");
		CompanyFacade fc = null;
		for (int i = 1; i < 100; i++) {
			fc =(CompanyFacade) c.login("company test " + i, "comptest" + i, UserType.COMPANY);
			System.out.println(fc);
			System.out.println("all of the company coupons-\n" + fc + "\n" + fc.getAllUserCoupons());
			System.out.println("******************************************************************************************");

		}
		System.out.println("coupons by the 'Baby' type:\n\n" + fc.getCouponsByType(CouponType.CAMPING));
		System.out.println("coupons by the start date: 2016/07/30 " + fc.getCouponsByMinStartDate(maxDate));
	}

	public static void consolePrintingCustomerCoupons(){
		System.out.println("MainSystemTest.consolePrintingCustomer()");
		System.out.println("******************************************************************************************************");
		CustomerFacade fc = null;
		for (int i = 1; i < 100; i++) {
			fc = (CustomerFacade) c.login("customer test " + i, "custtest" + i, UserType.CUSTOMER);
			System.out.println("all the coupons that the customer has purches-\n" + fc +"\n" + fc.getAllUserCoupons());
			System.out.println("*******************************************************************************************");
		}
	}
	public static void getCouponsByCompany(){
		System.out.println("MainSystemTest.getCouponsByCompany()");
		System.out.println("******************************************************************************************************");
		CompanyFacade fc = null;
		for (int i = 1; i < 100; i++) {
			fc =(CompanyFacade) c.login("company test " + i, "comptest" + i, UserType.COMPANY);
			System.out.println("COMPANY- " + fc + "\n" + fc.getAllUserCoupons());
		}
	}

	public static void removeCoupons(){
		System.out.println("MainSystemTest.removeCoupons()");
		System.out.println("******************************************************************************************************");
		CompanyFacade fc = null;
		for (int i = 1; i < 100; i++) {
			fc =(CompanyFacade) c.login("company test " + i, "comptest" + i, UserType.COMPANY);
			System.out.println(fc);
			Coupon coupon = fc.getCouponByTitle("title " + i);
			fc.removeCoupon(coupon);
			System.out.println("company " + fc + " remove the coupons:\n" + coupon );
			System.out.println("******************************************************************************************");
		}
	}

	public static void getAllCompanys(){
		System.out.println("MainSystemTest.getAllCompanys()");
		System.out.println("******************************************************************************************************");
		AdminFacade fc = (AdminFacade) c.login("admin", "1234", UserType.ADMIN);
		for (int i = 1; i < 100; i++) {
			System.out.println(fc.getUserByName("company test " + i, UserType.COMPANY));
		}
	}

	public static void getAllCustomers(){
		System.out.println("MainSystemTest.getAllCustomers()");
		System.out.println("******************************************************************************************************");
		AdminFacade fc = (AdminFacade) c.login("admin", "1234", UserType.ADMIN);
		for (int i = 1; i < 100; i++) {
			System.out.println(fc.getUserByName("customer test " + i, UserType.CUSTOMER));
		}
	}

	public static void removeCompanys(){
		System.out.println("MainSystemTest.removeCompanys()");
		System.out.println("******************************************************************************************************");
		AdminFacade fc = (AdminFacade) c.login("admin", "1234", UserType.ADMIN);
		for (int i = 1; i < 100; i++) {
			UserBase company = fc.getUserByName("company test " + i, UserType.COMPANY);
			fc.removeUser(company);
			System.out.println("the company have been remove: " + company);
		}
	}


	public static void removeCustomers(){
		System.out.println("MainSystemTest.removeCustomers()");
		System.out.println("******************************************************************************************************");
		AdminFacade fc = (AdminFacade) c.login("admin", "1234", UserType.ADMIN);
		for (int i = 1; i < 100; i++) {
			UserBase customer = fc.getUserByName("customer test " + i, UserType.CUSTOMER);
			fc.removeUser(customer);
			System.out.println("the customer have been removed: \n" + customer);
		}
	}


}
