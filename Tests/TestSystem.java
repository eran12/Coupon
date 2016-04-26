package Tests;

import java.sql.SQLException;

import com.ee.Base.UserType;
import com.ee.Exception.BaseException;
import com.ee.System.CouponSystem;

public class TestSystem {
	
	public static void main(String[] args) throws SQLException, BaseException {
		CouponSystem c = CouponSystem.getInstance();
		
/*		try {
		if (taf == 5) {
			c.login(new Admin(name, password));
		} else if (taf == 2) {
			c.login(new Company(name, password));
		} else {
			c.login(new Customer(name, password));
		}
		} catch (UserNotFoundException e) {
			
		} catch (Incorrectpswwords e) {
			// TODO: handle exception
		}
*/		
//		c.login(new Admin("eran", "era", null));
		c.login("era", "eran", UserType.ADMIN);
		CouponSystem.shutDown();
	}

}
