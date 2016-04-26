package com.ee.DBDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ee.Base.Coupon;
import com.ee.Base.Customer;
import com.ee.Base.UserBase;
import com.ee.ConnectionPool.DataSource;
import com.ee.Dao.CustomerDao;
import com.ee.Exception.BaseException;
import com.ee.Exception.CouponAmountException;
import com.ee.Exception.SQLExceptionHandler;

/**
 * this class implements the {@link CustomerDao} class.</br>
 * this is because if the DBDao layer will be replaced every thing will keep on working.</br>
 * this class is the Customer Facade in the system and all of the functions that a customer user can do.</br> 
 * @author Yossef Eran Eichenbaum
 *
 */
public class CustomerDBDao extends UserDBDao implements CustomerDao {

	@Override
	protected UserBase getNewUserInstance() {
		return new Customer();
	}


	/**
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt>.</br>
	 * the ResultSet is because its need to read from the DB.</br>
	 * the method set the coupon amount to the DB and reduce -1 ,</br>
	 * if anything want wrong it rollback the commit from the BD and throw an SQLException</br></br>
	 * 
	 * <strong>NOTE:</strong> the method is <strong>synchronized</strong>. this is to prevent a conflict in the DB.
	 * and its thread safe.</br>
	 * but if the amount of coupon get to 0 its throws a {@link CouponAmountException}.
	 * 
	 * 
	 * @finally it`s gives back the connection to pool and close the ResultSet.</br>
	 * @exception SQLException if anything went wrong its rollback and throw exception.
	 * @exception BaseException if the coupon amount is 0.
	 */
	@Override
	public synchronized void purchaseCoupon(Coupon coupon) throws SQLExceptionHandler,CouponAmountException {
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = "UPDATE coupon SET amount = ? WHERE id = ?";

		try{
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			int coupAmount = coupon.getAmount();
			if(coupAmount <= 0) throw new CouponAmountException("the amount of the coupon is: " + coupAmount);
			coupAmount = coupAmount-1;
			prepared.setInt(1, coupAmount);
			prepared.setLong(2, coupon.getId());
			prepared.executeUpdate();
			connection.commit();
		} 
		catch (SQLException | ClassNotFoundException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} finally {
			try {
				if(!prepared.isClosed() || !(prepared == null)){
					prepared.close();
				}
			} catch (SQLException e) {
				throw new SQLExceptionHandler("the PreperdStatment does not close.",
						SQLExceptionHandler.PREPERD_STATMENT);
			}
			DataSource.returnConnection(connection);
		}
	}

	/**
	 * this method get the user and coupon objects and sets there id`s in the JOIN table in the DB.</br>
	 */
	@Override
	public void insertToJoinUserCouponTable(UserBase user, Coupon coupon) {
		super.insertToJoinUserCouponTable(user, coupon);
	}

}
