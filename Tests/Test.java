package Tests;

import com.ee.Base.UserType;
import com.ee.Facade.UserFacade;
import com.ee.System.CouponSystem;

public class Test {
	public static void main(String[] args) {
		
		
		CouponSystem c= CouponSystem.getInstance();
		
		UserFacade facade = c.login("eran", "eran", UserType.CUSTOMER);
		System.out.println(facade.getCouponsLikeTitle("ti"));
		
	}
	
}
