package com.ee.DBDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ee.Base.Customer;
import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.ConnectionPool.DataSource;
import com.ee.Dao.UserDao;
import com.ee.Exception.BaseException;
import com.ee.Exception.IllegalPasswordException;
import com.ee.Exception.IllegalUserEmail;
import com.ee.Exception.IllegalUserName;
import com.ee.Exception.SQLExceptionHandler;

public abstract class UserDBDao extends CouponDBDao implements UserDao {

	/**
	 * this method should return a new instance of the beans by the class that extends it.
	 * @return a new instance of the the beans class.
	 */
	protected abstract UserBase getNewUserInstance();

	/**
	 * this method will return the user table name from the instance.
	 * @return String with the user table name.
	 */
	protected String getUserTableName() {
		return getNewUserInstance().getUserTableName();
	}

	/**
	 *
	 * the method get a connection from the <tt>connection-pool</tt>. </br>
	 * its use the prepared statement because its has couple of arguments.</br>
	 * its take the {@link UserBase} object that received and update all the attributes in the DB. </br>
	 * if anything want wrong is rollback the commit from the BD and throw an
	 * exception</br></br>
	 * 
	 * <strong> NOTE:</strong> the ID and NAME will not be updated.
	 * @param user {@link UserBase}
	 * @finally it`s gives back the connection to pool.</br>
	 *  
	 */
	@Override
	public void upDateUser(UserBase user) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = "UPDATE  " + user.getUserTableName() + " SET name = ?, password = ?, email = ? WHERE id = ?";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1, user.getUserName());
			prepared.setString(2, user.getPassword());
			prepared.setString(3, user.getEmail());
			prepared.setLong(4, user.getId());
			prepared.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new SQLExceptionHandler("the class not found", SQLExceptionHandler.SQLDRIVER_CLASS_NOT_FOUND);
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
	 * method create an object of UserBase from the DB by the given <tt>long</tt> id.</br>
	 * if the given id wont found it will return null.</br>
	 * 
	 * @finally it`s gives back the connection to pool.
	 * @return the object {@link UserBase} by the long "id" that given.</br>
	 *         if the given id wont found it will return null.
	 */
	@Override
	public UserBase getUserById(long id, UserType userType) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		ResultSet result = null;
		Customer customer = null ;
		String 	sqlstring = "SELECT * FROM " + userType.toString() + " WHERE id= ?";

		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, id);
			result = prepared.executeQuery();
			while(result.next()){
				customer = new Customer();
				customer.setId(result.getLong("id"));
				customer.setUserName(result.getNString("name"));
				customer.setPassword(result.getNString("password"));
				customer.setEmail(result.getNString("email"));
			}
		} 
		catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new SQLExceptionHandler("the class not found", SQLExceptionHandler.SQLDRIVER_CLASS_NOT_FOUND);
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
		return customer;
	}

	/**
	 * <p>
	 * the method get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the prepared statement because its has couple of arguments the
	 * method create an object of {@link UserBase} from the DB by the given name.</br>
	 * if the given name wont found it will return null.</br>
	 * 
	 * @param userName String
	 * @finally it`s gives back the connection to pool and close the ResultSet.
	 * @return the object {@link UserBase} by the String "userName" that given.</br>
	 *         if the given name wont found it will return null.
	 */
	@Override
	public UserBase getUserByName(String userName, UserType userType) throws SQLExceptionHandler {
		Connection connection = null;
		UserBase user = null;
		PreparedStatement prepared = null;
		String sqlstring = "SELECT * FROM " + userType.getType() + " WHERE name= ?";
		ResultSet result = null;
		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1, userName);
			result = prepared.executeQuery();

			while (result.next()) {

				user = getNewUserInstance();
				user.setId(result.getLong("id"));
				user.setUserName(result.getNString("name"));
				user.setPassword(result.getNString("password"));
				user.setEmail(result.getNString("email"));
				connection.commit();
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new SQLExceptionHandler("the class not found", SQLExceptionHandler.SQLDRIVER_CLASS_NOT_FOUND);
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
		return user;
	}

	/**
	 * the method know witch table to check the by the {@link UserType} </br>
	 * it search the name and after that the password.</br>
	 * if the user name or password not found it throws an exception.
	 * @param userName String
	 * @param password String
	 * @param userType {@link UserType}
	 */
	public boolean login(String userName, String password, UserType userType) throws SQLExceptionHandler {
		System.out.println(userName + password + userType);
		Connection connection = null;
		PreparedStatement prepared = null;
		ResultSet result = null;
		boolean loginapproved = false;
		String sqlstring = "SELECT name FROM " + userType.toString() + " WHERE name = ?";

		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1, userName);
			result = prepared.executeQuery();

			while (result.next()) {
				if (result.getString("name").equals(userName)) {
					sqlstring = "SELECT password FROM " + userType.toString() + " WHERE password = ?";
					prepared = connection.prepareStatement(sqlstring);
					prepared.setString(1, password);
					result.close();
					result = prepared.executeQuery();
					while (result.next()) {
						if (result.getString("password").equals(password)) {
							loginapproved = true;
						}
						return loginapproved;
					}
				}
			}
		}catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new SQLExceptionHandler("the class not found", SQLExceptionHandler.SQLDRIVER_CLASS_NOT_FOUND);
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
		return loginapproved;
	}

	/**
	 * <p>
	 * the method get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the prepared statement because its has couple of arguments.</br>
	 * this method checks with the DB the user email.</br>
	 * 
	 * @finally it`s gives back the connection to pool.
	 * @return it return <tt>String</tt> with the user email from the DB.</br>
	 */
	@Override
	public String getUserEmail(UserBase user) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = "SELECT email FROM " + getUserTableName() + " WHERE email =?";

		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1, user.getEmail());
		}catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new SQLExceptionHandler("the class not found", SQLExceptionHandler.SQLDRIVER_CLASS_NOT_FOUND);
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

		return user.getEmail();
	}

	/**
	 * <p>
	 * the method get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the prepared statement because its has couple of arguments.</br>
	 * this method checks with the DB if the given user email exist.</br>
	 * if it dose it throws BaseException.</br>
	 * if the given email is valid <tt>(checks in class {@link UserBase})</tt> it sets
	 * the email in the DB by the user id.</br>
	 * 
	 * @param user {@link UserBase} object type.
	 * @param userEmail String.
	 * @finally it`s gives back the connection to pool and close the ResultSet.
	 */
	@Override
	public void setUserEmail(UserBase user, String userEmail) throws SQLExceptionHandler,IllegalUserEmail {
		Connection connection = null;
		PreparedStatement prepared = null;
		ResultSet result = null;
		String sqlstring = "SELECT email FROM " + user.getUserTableName() + " WHERE id = ?";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, user.getId());
			result = prepared.executeQuery();

			while (result.next()) {

				if (!result.getString("email").equals(userEmail)) {

					user.setEmail(userEmail);
					sqlstring = "UPDATE " + user.getUserTableName() + " SET email = ? WHERE id =?";
					prepared = connection.prepareStatement(sqlstring);
					prepared.setLong(2, user.getId());
					prepared.setString(1, user.getEmail());
					prepared.executeUpdate();

				} else {
					throw new IllegalUserEmail("email allredy exist");
				}
				connection.commit();
			}
		}catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new SQLExceptionHandler("the class not found", SQLExceptionHandler.SQLDRIVER_CLASS_NOT_FOUND);
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
	}

	/**
	 * the method get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the prepared statement because its has couple of arguments.</br>
	 * this method checks with the DB if the user name is correct and after
	 * that if the email is correct.</br>
	 * if every thing went throw it gives back <tt>String</tt> the user password from
	 * the DB.</br>
	 * 
	 * @param userName String.
	 * @param userEmail String.

	 * @finally it`s gives back the connection to pool and close the ResultSet.
	 * @return it return <tt>String</tt> with the password.</br>
	 *  if the given name and email is correct.</br>
	 */
	@Override
	public String getUserPassword(String userName, String userEmail, UserType userType) throws 
	SQLExceptionHandler,IllegalUserEmail,IllegalUserName {
		Connection connection = null;
		PreparedStatement prepared = null;
		ResultSet result = null;
		String pass = null;
		String sqlstring = "SELECT name FROM " + userType.toString() + " WHERE name = ?";

		try {
			connection = DataSource.getConnection();
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1, userName);
			result = prepared.executeQuery();

			while (result.next()) {

				if (result.getString("name").equals(userName)) {

					sqlstring = "SELECT email FROM " + userType.toString() + " WHERE email = ?";
					prepared = connection.prepareStatement(sqlstring);
					prepared.setString(1, userEmail);
					result.close();
					result = prepared.executeQuery();

					while (result.next()) {
						if (result.getString("email").equals(userEmail)) {

							sqlstring = "SELECT password FROM " + userType.toString() + " WHERE name = ?";
							prepared = connection.prepareStatement(sqlstring);
							prepared.setString(1, userName);


							result.close();
							result = prepared.executeQuery();
							while (result.next()) {
								pass = result.getString("password");
							}
							return pass;
						} else {
							throw new IllegalUserEmail("The E-Mail was incorrect");
						}
					}
				} else {
					throw new IllegalUserName("User Name does not exist");
				}
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new SQLExceptionHandler("the class not found", SQLExceptionHandler.SQLDRIVER_CLASS_NOT_FOUND);
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
		return pass;
	}

	/**
	 * <p>
	 * the method get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the prepared statement because its has couple of arguments.</br>
	 * this method checks with the DB if the user current password is exact like the password in the DB
	 * and after that if the email is exact like in the DB.</br>
	 * if every thing went throw it sets the user new-password to the DB by
	 * the user ID.</br>
	 * 
	 * @param user UserBase type object.
	 * @param email String.
	 * @param oldPassword String.
	 * @param newPassword String.
	 * @finally it`s gives back the connection to pool and close the ResultSet.
	 */
	@Override
	public void setUserPassword(UserBase user, String email, String oldPassword, String newPassword)
			throws SQLExceptionHandler,IllegalUserEmail,IllegalPasswordException {
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = "SELECT password FROM " + user.getUserTableName() + " WHERE id = ?";
		ResultSet result = null;
		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, user.getId());
			result = prepared.executeQuery();

			while (result.next()) {

				if (result.getString("password").equals(oldPassword)) {

					sqlstring = "SELECT email FROM " + user.getUserTableName() + " WHERE id = ?";
					prepared = connection.prepareStatement(sqlstring);
					prepared.setLong(1, user.getId());
					result.close();
					result = prepared.executeQuery();
					while (result.next()) {
						if (result.getString("email").equals(email)) {

							user.setPassword(newPassword);
							sqlstring = "UPDATE " + user.getUserTableName() + " SET password = ? WHERE id = ?";
							prepared = connection.prepareStatement(sqlstring);
							prepared.setString(1, newPassword);
							prepared.setLong(2, user.getId());
							prepared.executeUpdate();

						} else {
							throw new IllegalUserEmail("The email wasn`t found");
						}
					}
				} else {
					throw new IllegalPasswordException("the old password is not currect");
				}
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new SQLExceptionHandler("the class not found", SQLExceptionHandler.SQLDRIVER_CLASS_NOT_FOUND);
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
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
