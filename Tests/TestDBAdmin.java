package Tests;

import java.sql.SQLException;

import com.ee.Base.Admin;
import com.ee.Base.UserType;
import com.ee.DBDao.AdminDBDao;
import com.ee.Exception.BaseException;

public class TestDBAdmin {
	
	public static void main(String[] args) throws ClassNotFoundException, BaseException, SQLException {
		AdminDBDao a = new AdminDBDao();
		System.out.println(a.getAllUsers(UserType.COMPANY));
		//a.createUser(new Admin("erannn", "password", "email@e.com"));
		System.out.println(a.getAllUsers(UserType.ADMIN));
		
	}

}
