package Tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ee.Base.Coupon;
import com.ee.ConnectionPool.DataSource;
import com.ee.DBDao.CouponDBDao;
import com.ee.Exception.BaseException;
import com.ee.Exception.SQLExceptionHandler;

public class IdTest {
	public static void main(String[] args) {
		

//		Connection connection = null;
//		ResultSet result = null;
//		Statement prepared = null;
//		long id = 0;
//		long idtemp = 0;
//		String sqlstring = "SELECT id FROM coupon";
//
//		try {
//			connection = DataSource.getConnection();
//			prepared = connection.createStatement();
//			result = prepared.executeQuery(sqlstring);
//			while (result.next()) {
//				id = result.getRow();
//				idtemp = result.getLong("id");
//				if (idtemp > id) {
//					System.out.println(id);
//				}
//			}
//		} catch (SQLException | ClassNotFoundException e) {
//			throw new BaseException(e.getMessage());
//		} finally {
//			try {
//				result.close();
//			} catch (SQLException e) {
//				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
//			}
//			try {
//				prepared.close();
//			} catch (SQLException e) {
//				throw new SQLExceptionHandler("the Statment does not close.", SQLExceptionHandler.PREPERD_STATMENT);
//			}
//			DataSource.returnConnection(connection);
//		}
//		id++;
//		System.out.println(id);
	}
}

