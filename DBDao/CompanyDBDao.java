package com.ee.DBDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ee.Base.Company;
import com.ee.Base.Coupon;
import com.ee.Base.UserBase;
import com.ee.ConnectionPool.DataSource;
import com.ee.Dao.CompanyDao;
import com.ee.Exception.BaseException;
import com.ee.Exception.SQLExceptionHandler;

public class CompanyDBDao extends UserDBDao implements CompanyDao {

	/**
	 * this method make a new instance for the UserDBDao layer.
	 */
	@Override
	protected UserBase getNewUserInstance() {
		return new Company();
	}

	/**
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt> because its has couple of arguments.</br>
	 * the method create a {@link Coupon} in the DB by all of its attributes.</br>
	 * if anything want wrong it rollback the commit from the BD and throw an </br>
	 * 
	 * 
	 * @finally it`s gives back the connection to pool and close the prepared statement connection.</br>
	 * @throws SQLExceptionHandler 
	 */
	@Override
	public synchronized void createCoupon(Coupon coupon) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = "INSERT INTO coupon VALUES (?,?,?,?,?,?,?,?,?)";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, coupon.getId());
			prepared.setString(2, coupon.getTitle());
			prepared.setDate(3, java.sql.Date.valueOf(coupon.getStartDate()));
			prepared.setDate(4,java.sql.Date.valueOf(coupon.getEndDate()));
			prepared.setInt(5, coupon.getAmount());
			prepared.setString(6, coupon.getType().getName());
			prepared.setString(7, coupon.getMessage());
			prepared.setDouble(8, coupon.getPrice());
			prepared.setString(9, coupon.getImage());
			prepared.execute();
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
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt> to know witch coupon to delete it uses the id of the coupon.</br>
	 * if anything want wrong it rollback the commit from the BD and throw an SQLException</br>
	 * 
	 * @finally it`s gives back the connection to pool and close the prepared statement connection.</br>
	 * @throws SQLExceptionHandler
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		try {
			connection = DataSource.getConnection();
			String sqlstring = "DELETE FROM coupon WHERE id = ?";
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, coupon.getId());
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
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt> because its has couple of arguments.</br>
	 * it update all the parameters except the id and the title of the coupon.</br>
	 * if anything want wrong it rollback the commit from the DB and throw a {@link BaseException}</br>
	 * 
	 * @finally it`s gives back the connection to pool and close the prepared statement connection.</br>
	 * @throws SQLExceptionHandler
	 */
	@Override
	public void upDateCoupon(Coupon coupon) throws SQLExceptionHandler {

		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = "UPDATE coupon SET title = ?, start_date = ?, end_date = ?, amount = ?, type = ?,"
				+ " message = ?, price = ?, image = ? WHERE id = ?"; 

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1, coupon.getTitle());
			prepared.setDate(3,java.sql.Date.valueOf(coupon.getStartDate()));
			prepared.setDate(4,java.sql.Date.valueOf(coupon.getEndDate()));
			prepared.setInt(4, coupon.getAmount());
			prepared.setString(5, coupon.getType().getName());
			prepared.setString(6, coupon.getMessage());
			prepared.setDouble(7, coupon.getPrice());
			prepared.setString(8, coupon.getImage());
			prepared.setLong(9, coupon.getId());
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

}
