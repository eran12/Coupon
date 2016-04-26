package com.ee.DBDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.ee.Base.Coupon;
import com.ee.Base.CouponType;
import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.ConnectionPool.DataSource;
import com.ee.Dao.CouponDao;
import com.ee.Exception.BaseException;
import com.ee.Exception.SQLExceptionHandler;

public class CouponDBDao implements CouponDao {

	/**
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt> because its has couple of arguments.</br>
	 * the ResultSet is because its reading from the DB.</br>
	 * the method search in the coupon table by the given <tt>long</tt> ID.</br>
	 * if anything want wrong it rollback the commit from the BD and throw an SQLExceptionHandler</br></br>
	 * 
	 * @return {@link Coupon} of the given id. if there is no coupon in that id it will return null.
	 * @finally it`s gives back the connection to pool and close the ResultSet.</br>
	 */
	@Override
	public Coupon getCouponById(long id) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		Coupon coupon = new Coupon();
		String sqlstring = "SELECT * FROM coupon WHERE id = ?";
		ResultSet result =null;
		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, id);
			result = prepared.executeQuery();

			while(result.next()){
				coupon.setId(result.getLong("id"));
				coupon.setTitle(result.getString("title"));
				Date sqlDate = result.getDate("start_date");
				coupon.setStartDate(sqlDate.toLocalDate());
				sqlDate = result.getDate("end_date");
				coupon.setEndDate(sqlDate.toLocalDate());
				coupon.setAmount(result.getInt("amount"));
				coupon.setMessage(result.getString("message"));
				coupon.setPrice(result.getDouble("price"));
				coupon.setType((CouponType.valueOf((String) result.getObject("type"))));
				coupon.setImage(result.getString("image"));
			}

		} 
		catch (SQLException | ClassNotFoundException e) {
			throw new BaseException(e.getMessage());
		} finally {
			try {
				if(!result.isClosed() || !(result == null)){
					result.close();	
				}
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
			}
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
		return coupon;
	}

	/**
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt> because its has couple of arguments.</br>
	 * the ResultSet is because its reading from the DB.</br>
	 * the method search in the coupon table by the given <tt>String</tt> coupon title.</br>
	 * if anything want wrong it rollback the commit from the BD and throw an SQLExceptionHandler</br></br>
	 * <strong>NOTE:</strong> the search for the coupon by title is word case sensitive. 
	 * @return {@link Coupon} of the given id. if there is no coupon in that id it will return null.
	 * @finally it`s gives back the connection to pool and close the ResultSet.</br>
	 */
	@Override
	public Coupon getCouponByTitle(String couponTitle) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		Coupon coupon = new Coupon();
		String sqlstring = "SELECT * FROM coupon WHERE title = ?";
		ResultSet result =null;
		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1,"%" + couponTitle + "%");
			result = prepared.executeQuery();

			while(result.next()){
				coupon.setId(result.getLong("id"));
				coupon.setTitle(result.getString("title"));
				Date sqlDate = result.getDate("start_date");
				coupon.setStartDate(sqlDate.toLocalDate());
				sqlDate = result.getDate("end_date");
				coupon.setEndDate(sqlDate.toLocalDate());
				coupon.setAmount(result.getInt("amount"));
				coupon.setMessage(result.getString("message"));
				coupon.setPrice(result.getDouble("price"));
				coupon.setType((CouponType.valueOf((String) result.getObject("type"))));
				coupon.setImage(result.getString("image"));
			}

		} 
		catch (SQLException | ClassNotFoundException e) {
			throw new BaseException(e.getMessage());
		} finally {
			try {
				if(!result.isClosed() || !(result == null)){
					result.close();	
				}
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
			}
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
		return coupon;
	}

	@Override
	public Collection<Coupon> getCouponsLikeTitle(String couponTitle) throws BaseException {
		Connection connection = null;
		PreparedStatement prepared = null;
		Coupon coupon = null;
		String sqlstring = "SELECT * FROM coupon WHERE title LIKE ?";
		ResultSet result =null;
		ArrayList<Coupon> coupons = new ArrayList<>();
		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1,"%" + couponTitle + "%");
			result = prepared.executeQuery();
			while(result.next()){
				coupon = new Coupon();
				coupon.setId(result.getLong("id"));
				coupon.setTitle(result.getString("title"));
				Date sqlDate = result.getDate("start_date");
				coupon.setStartDate(sqlDate.toLocalDate());
				sqlDate = result.getDate("end_date");
				coupon.setEndDate(sqlDate.toLocalDate());
				coupon.setAmount(result.getInt("amount"));
				coupon.setMessage(result.getString("message"));
				coupon.setPrice(result.getDouble("price"));
				coupon.setType((CouponType.valueOf((String) result.getObject("type"))));
				coupon.setImage(result.getString("image"));
				coupons.add(coupon);
			}

		} 
		catch (SQLException | ClassNotFoundException e) {
			throw new BaseException(e.getMessage());
		} finally {
			try {
				if(!result.isClosed() || !(result == null)){
					result.close();	
				}
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
			}
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
		return coupons;
	}

	/**
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt>.</br>
	 * the ResultSet is because its need to read from the DB.</br>
	 * the method get all the coupons from the coupon table in the DB,</br>
	 * it create the coupon object and put it in to a <tt>Collection</tt> {@link LinkedList}.</br>
	 * if anything want wrong it rollback the commit from the BD and throw a {@link SQLExceptionHandler}</br></br>
	 * 
	 * <strong>NOTE:</strong> the returned Collection is unmodifiable-Collection.
	 * @return <strong>Collection</strong> with all <tt>Coupons</tt> in the DB.
	 * @finally it`s gives back the connection to pool and close the ResultSet and the Statement.</br>
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws SQLExceptionHandler {
		Connection connection = null;
		Collection<Coupon> coupon = new LinkedList<>();

		Statement statement = null;
		ResultSet result = null ;
		String sqlstring = "SELECT * FROM coupon";

		try {
			connection = DataSource.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(sqlstring);
			while(result.next()){
				Coupon cop = new Coupon();
				cop.setId(result.getLong("id"));
				cop.setTitle(result.getString("title"));
				Date sql = result.getDate("start_date");
				cop.setStartDate(sql.toLocalDate());
				sql = result.getDate("end_date");
				cop.setEndDate(sql.toLocalDate());
				cop.setAmount(result.getInt("amount"));
				cop.setMessage(result.getString("message"));
				cop.setPrice(result.getDouble("price"));
				cop.setType((CouponType.valueOf((String) result.getObject("type"))));
				cop.setImage(result.getString("image"));
				coupon.add(cop);
			}
		}
		catch (SQLException | ClassNotFoundException e) {
		} finally {
			try {
				if(!result.isClosed() || !(result == null)){
					result.close();	
				}
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
			}
			try {
				if(!statement.isClosed() || !(statement == null)){
					statement.close();
				}
			} catch (SQLException e) {
				throw new SQLExceptionHandler("the PreperdStatment does not close.",
						SQLExceptionHandler.PREPERD_STATMENT);
			}
			DataSource.returnConnection(connection);
		}
		return Collections.unmodifiableCollection(coupon);
	}

	/**
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt>.</br>
	 * the ResultSet is because its need to read from the DB.</br>
	 * the method get coupons from the coupon table by the given <tt>Enum type</tt> from the DB,</br>
	 *  and create the coupon object and put it in to the <tt>Collection</tt>.</br>
	 * if anything want wrong it rollback the commit from the BD and throw an {@link SQLExceptionHandler}</br></br>
	 * 
	 * <strong>NOTE:</strong> the returned Collection is unmodifiable-Collection.
	 * @return <strong>Collection</strong> with <tt>Coupons</tt> objects by the given {@link CouponType}.
	 * @finally it`s gives back the connection to pool and close the ResultSet and the prepared statement.</br>
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType type) throws SQLExceptionHandler {
		Connection connection = null;
		Collection<Coupon> coupon = new HashSet<>();
		ResultSet result = null;
		PreparedStatement prepared = null;

		String sqlstring = "SELECT * FROM coupon WHERE type = ?";

		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1, type.getName());
			result = prepared.executeQuery();

			while(result.next()){
				Coupon cop = new Coupon();
				cop.setId(result.getLong("id"));
				cop.setTitle(result.getString("title"));
				Date sql = result.getDate("start_date");
				cop.setStartDate(sql.toLocalDate());
				sql = result.getDate("end_date");
				cop.setEndDate(sql.toLocalDate());
				cop.setAmount(result.getInt("amount"));
				cop.setMessage(result.getString("message"));
				cop.setPrice(result.getDouble("price"));
				cop.setType((CouponType.valueOf((String) result.getObject("type"))));
				cop.setImage(result.getString("image"));
				coupon.add(cop);
			}
		}
		catch (SQLException | ClassNotFoundException e) {
		} finally {
			try {
				if(!result.isClosed() || !(result == null)){
					result.close();	
				}
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
			}
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
		return Collections.unmodifiableCollection(coupon);
	}

	/**
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt>.</br>
	 * the ResultSet is because its need to read from the DB.
	 * the method get from the coupon table by the given <tt>long ID</tt> from the DB,
	 *  create the coupon object and put it in to the <tt>Collection</tt>.</br>
	 * if anything want wrong it rollback the commit from the DB and throw an SQLExceptionHandler</br></br>
	 * 
	 * <strong>NOTE:</strong> the returned Collection is unmodifiable-Collection.
	 * @return <strong>Collection</strong> with <tt>Coupons</tt> objects of the given company id.
	 * @finally it`s gives back the connection to pool and close the ResultSet and the prepared statement.</br>
	 */
	@Override
	public Collection<Coupon> getCouponsByCompany(long id) throws SQLExceptionHandler {
		Connection connection = null;
		Collection<Coupon> coupon = new LinkedList<>();
		PreparedStatement prepared = null;
		ResultSet result = null;
		System.out.println(id);
		String sqlstring = "SELECT * FROM company_coupon JOIN coupon ON coupon_id = id WHERE user_id = ?";

		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, id);
			result = prepared.executeQuery();
			while(result.next()){
				Coupon cop = new Coupon();
				cop.setId(result.getLong("id"));
				cop.setTitle(result.getString("title"));
				Date sql = result.getDate("start_date");
				cop.setStartDate(sql.toLocalDate());
				sql = result.getDate("end_date");
				cop.setEndDate(sql.toLocalDate());
				cop.setAmount(result.getInt("amount"));
				cop.setMessage(result.getString("message"));
				cop.setPrice(result.getDouble("price"));
				cop.setType((CouponType.valueOf((String) result.getObject("type"))));
				cop.setImage(result.getString("image"));
				coupon.add(cop);
				System.out.println(result.isClosed());
			}
		}
		catch (SQLException | ClassNotFoundException e) {
		} 
		finally {
			try {
				if(result.isClosed()){

					result.close();	
				}
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
			}
			try {
				if(!prepared.isClosed()){
					prepared.close();
				}
			} catch (SQLException e) {
				throw new SQLExceptionHandler("the PreperdStatment does not close.",
						SQLExceptionHandler.PREPERD_STATMENT);
			}
			DataSource.returnConnection(connection);
		}
		return Collections.unmodifiableCollection(coupon);
	}

	/**
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt>.</br>
	 * the ResultSet is because its need to read from the DB.
	 * the method get from the coupon table by the given <tt>LocalDate start time</tt> from the DB,
	 *  create the coupon object and put it in to the <tt>Collection</tt>.</br>
	 *  the method uses <tt>java.sql.Date</tt> to convert from the <tt>LocalDate</tt>.</br>
	 * if anything want wrong it rollback the commit from the BD and throw an SQLException</br></br>
	 * 
	 * <strong>NOTE:</strong> the returned Collection is unmodifiable-Collection.</br>
	 * @return <strong>Collection</strong> with <tt>Coupons</tt> object from the lower to the given max start date.
	 * @finally it`s gives back the connection to pool and close the ResultSet and the prepared statement.</br>
	 */
	@Override
	public Collection<Coupon> getCouponsByMinStartDate(LocalDate minStartDate) throws SQLExceptionHandler {
		Connection connection = null;
		Collection<Coupon> coupon = new LinkedList<>();
		PreparedStatement prepared = null;
		ResultSet result = null;

		String sqlstring = "SELECT * FROM coupon WHERE start_date > ?";

		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setDate(1,java.sql.Date.valueOf(minStartDate));
			result = prepared.executeQuery();
			while(result.next()){
				Coupon cop = new Coupon();
				cop.setId(result.getLong("id"));
				cop.setTitle(result.getString("title"));
				Date sql = result.getDate("start_date");
				cop.setStartDate(sql.toLocalDate());
				sql = result.getDate("end_date");
				cop.setEndDate(sql.toLocalDate());
				cop.setAmount(result.getInt("amount"));
				cop.setMessage(result.getString("message"));
				cop.setPrice(result.getDouble("price"));
				cop.setType((CouponType.valueOf((String) result.getObject("type"))));
				cop.setImage(result.getString("image"));
				coupon.add(cop);
			}
		}
		catch (SQLException | ClassNotFoundException e) {
		} finally {
			try {
				if(!result.isClosed() || result != null){
					result.close();	
				}
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
			}
			try {
				if(!prepared.isClosed() || prepared != null){
					prepared.close();
				}
			} catch (SQLException e) {
				throw new SQLExceptionHandler("the PreperdStatment does not close.",
						SQLExceptionHandler.PREPERD_STATMENT);
			}
			DataSource.returnConnection(connection);
		}
		return Collections.unmodifiableCollection(coupon);
	}

	/**
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt>.</br>
	 * the ResultSet is because its need to read from the DB.
	 * the method get from the coupon table by the given <tt>LocalDate end time</tt> from the DB,
	 * create the coupon object and put it in to the <tt>Collection</tt>.</br>
	 * the method uses <tt>java.sql.Date</tt> to convert from the <tt>LocalDate</tt>.</br>
	 * if anything want wrong it rollback the commit from the BD and throw an SQLException</br></br>
	 * 
	 * <strong>NOTE:</strong> the returned Collection is unmodifiable-Collection.
	 * @return <strong>Collection</strong> with <tt>Coupons</tt> object from the lower to the given end date.
	 * @finally it`s gives back the connection to pool and close the ResultSet and the prepared statement.</br>
	 */
	@Override
	public Collection<Coupon> getCouponsByMaxEndDate(LocalDate maxEndDate) throws SQLExceptionHandler {
		Connection connection = null;
		Collection<Coupon> coupon = new LinkedList<>();
		PreparedStatement prepared = null;
		ResultSet result = null;

		String sqlstring = "SELECT * FROM coupon WHERE end_date <= ?";

		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setDate(1,java.sql.Date.valueOf(maxEndDate));
			result = prepared.executeQuery();
			while(result.next()){
				Coupon cop = new Coupon();
				cop.setId(result.getLong("id"));
				cop.setTitle(result.getString("title"));
				Date sql = result.getDate("start_date");
				cop.setStartDate(sql.toLocalDate());
				sql = result.getDate("end_date");
				cop.setEndDate(sql.toLocalDate());
				cop.setAmount(result.getInt("amount"));
				cop.setMessage(result.getString("message"));
				cop.setPrice(result.getDouble("price"));
				cop.setType((CouponType.valueOf((String) result.getObject("type"))));
				cop.setImage(result.getString("image"));
				coupon.add(cop);
			}
		}
		catch (SQLException | ClassNotFoundException e) {
		} finally {
			try {
				if(!result.isClosed() || !(result == null)){
					result.close();	
				}
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
			}
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
		return Collections.unmodifiableCollection(coupon);
	}

	/**
	 * first get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the <tt>prepared-statement</tt>.</br>
	 * the ResultSet is because its need to read from the DB.
	 * the method get from the coupon table by the given <tt>int price</tt> from the DB,
	 * create the coupon object and put it in to the <tt>Collection</tt>.</br>
	 * the method uses <tt>java.util.Date</tt> to convert from the <tt>java.sql.Date</tt>.</br>
	 * if anything want wrong it rollback the commit from the BD and throw an SQLException</br></br>
	 * 
	 * <strong>NOTE:</strong> the returned Collection is unmodifiable-Collection.
	 * @return <strong>Collection</strong> with <tt>Coupons</tt> object from the lower to the given max price.
	 * @finally it`s gives back the connection to pool and close the ResultSet and the prepared statement.</br>
	 */
	@Override
	public Collection<Coupon> getCouponsByMaxPrice(double maxPrice) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		ResultSet result = null;
		Collection<Coupon> coupon = new HashSet<>();

		String sqlstring = "SELECT * FROM coupon WHERE price >= ?";

		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setDouble(1, maxPrice);
			result = prepared.executeQuery();

			while(result.next()){
				Coupon cop = new Coupon();
				cop.setId(result.getLong("id"));
				cop.setTitle(result.getString("title"));
				Date sql = result.getDate("start_date");
				cop.setStartDate(sql.toLocalDate());
				sql = result.getDate("end_date");
				cop.setEndDate(sql.toLocalDate());
				cop.setAmount(result.getInt("amount"));
				cop.setMessage(result.getString("message"));
				cop.setPrice(result.getDouble("price"));
				cop.setType((CouponType.valueOf((String) result.getObject("type"))));
				cop.setImage(result.getString("image"));
				cop.setId(result.getLong("comp_id"));
				coupon.add(cop);
			}
		}
		catch (SQLException | ClassNotFoundException e) {
		} finally {
			try {
				if(!result.isClosed() || !(result == null)){
					result.close();	
				}
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
			}
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
		return Collections.unmodifiableCollection(coupon);
	}

	/**
	 * <p>
	 * the method get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the prepared statement because its has couple of arguments.</br></br>
	 * <strong> NOTE:</strong> the method needs to called after company create coupon or customer perches a coupon because the
	 * <tt>JOIN</tt> table wont be updated automatically.</br>
	 * 
	 * @param user {@link UserBase}
	 * @param coupon {@link Coupon}
	 * @finally it`s gives back the connection to pool and close the prepared statement.
	 */
	@Override
	public void insertToJoinUserCouponTable(UserBase user, Coupon coupon) throws SQLExceptionHandler {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = "select * from " + user.getUserTableName() + " where name = ?";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1, user.getUserName());
			result = prepared.executeQuery();

			while (result.next()) {
				long id = result.getLong("id");
				user.setId(id);
			}
			result.close();
			prepared.close();
			sqlstring = "select * from coupon where title = ?";
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1, coupon.getTitle());
			result = prepared.executeQuery();

			while (result.next()) {
				long id = result.getLong("id");
				coupon.setId(id);
			}
			result.close();
			prepared.close();
			sqlstring = "INSERT " + user.getCouponTableName() + " SET user_id =? ,coupon_id = ?  ";
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, user.getId());
			prepared.setLong(2, coupon.getId());
			prepared.execute();
			connection.commit();
		} 
		catch (SQLException | ClassNotFoundException e) {
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
	 * the method get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the prepared statement because its has couple of arguments the
	 * method create a Collection of Coupons for the user from the DB by the JOIN tables.</br>
	 * if there is no coupons the Collection will return empty.</br>
	 * 
	 * @finally it`s gives back the connection to pool and close the ResultSet.
	 * @return Collection of coupons of the {@link UserBase} that ask.</br>
	 *         if the user don`t have any coupons it will return empty
	 *         Collection.
	 */
	@Override
	public Collection<Coupon> getAllUserCoupons(UserBase user) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		ResultSet result = null;
		String sqlstring = "SELECT * FROM " + user.getCouponTableName() +
				" JOIN coupon ON coupon_id = id WHERE user_id = ?";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, user.getId());
			result = prepared.executeQuery();
			while (result.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(result.getLong("id"));
				coupon.setTitle(result.getString("title"));
				Date sql = result.getDate("start_date");
				coupon.setStartDate(sql.toLocalDate());
				sql = result.getDate("end_date");
				coupon.setEndDate(sql.toLocalDate());
				coupon.setAmount(result.getInt("amount"));
				coupon.setType((CouponType.valueOf((String) result.getObject("type"))));
				coupon.setMessage(result.getString("message"));
				coupon.setPrice(result.getDouble("price"));
				coupon.setImage(result.getString("image"));
				user.setCoupons(coupon);
				connection.commit();
			}
		}
		catch (SQLException | ClassNotFoundException e) {
		} finally {
			try {
				if(!result.isClosed() || !(result == null)){
					result.close();	
				}
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the ResultSet is not closed." , SQLExceptionHandler.RESULT_SET);
			}
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
		return user.getCoupons();
	}

	/**
	 * the method will call to the method {@link #getUsersTypes()} and will run throw the array of all the users types.</br>
	 * and remove them from the joins tables.</br>
	 * @see {@link #getUsersTypes()}
	 * @param coupon {@link Coupon} the coupon that will be removed.
	 * @finally the method will return the connection to the pool and close the prepared statement.
	 */
	@Override
	public void removeFromJoinUserCouponTable(Coupon coupon) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = null;

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			Set<UserType> userTypes = getUsersTypes();
			for(UserType type : userTypes){
				sqlstring = "DELETE FROM " + type.getType().toString() + "_coupon WHERE coupon_id = ?" ;
				prepared = connection.prepareStatement(sqlstring);
				prepared.setLong(1, coupon.getId());
				prepared.executeUpdate();
				connection.commit();
			}

			prepared.closeOnCompletion();
		}
		catch (SQLException | ClassNotFoundException e) {
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
	 * this method get all types of users from the {@link UserType} class.</br>
	 * and return it in a Set Collection.</br></br>
	 * <strong> NOTE:</strong> the set will not contain the ADMIN.
	 * @return Set with all of the {@link UserType} attributes. but not with the admin type.
	 */
	private Set<UserType> getUsersTypes(){
		Set<UserType> userType = EnumSet.allOf(UserType.class);
		for (UserType type : userType) {
			if(type.equals(UserType.ADMIN)){
				userType.remove(type);
			}
		}
		return userType;
	}
}
