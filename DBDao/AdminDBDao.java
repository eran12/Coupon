package com.ee.DBDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import com.ee.Base.Admin;
import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.Exception.BaseException;
import com.ee.Exception.CreatingUserInDBException;
import com.ee.Exception.SQLExceptionHandler;
import com.ee.ConnectionPool.DataSource;
import com.ee.Dao.AdminDao;

public class AdminDBDao extends UserDBDao implements AdminDao {

	/**
	 * this method make a new instance for the UserDBDao layer.
	 */
	@Override
	protected UserBase getNewUserInstance() {
		return new Admin();
	}
	
	/**
	 * the method get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the prepared statement because its has couple of arguments, and the ResultSet is
	 * because its reading from the DB.</br>
	 *  its check if the <tt>user name</tt> is already
	 * exist in the DB and if it dose its throws {@link CreatingUserInDBException}</br>
	 * if the name dose not exist it create instance and write the user to the DB.</br>
	 * if anything went wrong its rollback the commit from the BD and throw an {@link SQLExceptionHandler}</br>
	 * 
	 * @param user type of {@link UserBase}.
	 * @finally it`s gives back the connection to pool and close the
	 *          ResultSet.</br>
	 * @throws SQLExceptionHandler
	 */
	@Override
	public synchronized void createUser(UserBase user) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = "INSERT INTO  " + user.getUserTableName() + " VALUES (?,?,?,?)";
		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, user.getId());
			prepared.setString(2, user.getUserName());
			prepared.setString(3, user.getPassword());
			prepared.setString(4, user.getEmail());
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
			throw new SQLExceptionHandler(e.getMessage() + this.getClass());
		} finally {
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
	}

	/**
	 * the method get a connection from the <tt>connection-pool</tt>.</br>
	 * its use the prepared statement because its has couple of arguments</br>
	 * if anything want wrong is rollback the commit from the BD and throw an
	 * exception</br>
	 * 
	 * @param UserBase user
	 * @throws SQLExceptionHandler
	 * @finally it`s gives back the connection to pool.
	 */
	@Override
	public void removeUser(UserBase user) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = "DELETE FROM  " + user.getUserTableName() + " WHERE name = ?";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setString(1, user.getUserName());
			prepared.executeUpdate();
			connection.commit();
		} catch (SQLException | ClassNotFoundException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} finally {
			try {
				if(!prepared.isClosed()|| prepared != null){
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
	 * its use the prepared statement because its has couple of arguments</br>
	 * the method DELETE the user from the DB. it uses the {@link UserType} to know witch table to look for.</br>
	 * and remove by the id of the user.</br>
	 * if anything want wrong is rollback the commit from the BD and throw an
	 * exception</br>
	 * 
	 * @param UserBase user
	 * @throws SQLExceptionHandler
	 * @finally it`s gives back the connection to pool.
	 */
	@Override
	public void removeUserById(UserType userType, long userId) throws SQLExceptionHandler {
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlstring = "DELETE FROM  " + userType.toString() + " WHERE id = ?";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlstring);
			prepared.setLong(1, userId);
			prepared.executeUpdate();
			connection.commit();
		} catch (SQLException  e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new SQLExceptionHandler(e.getMessage());
		} catch (ClassNotFoundException e) {
			
			throw new BaseException(e.getMessage());
		} finally {
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
	}

	/**
	 * the method get a connection from the <tt>connection-pool</tt>, its use the java statement.</br>
	 * the method create a Collection of all user by the {@link UserType} from the DB .</br>


	 * 
	 * @return the Collection of {@link UserBase} by the given {@link UserType}.</br>
	 * @finally it`s gives back the connection to pool and close the ResultSet.
	 * @throws BaseException
	 */
	@Override
	public Collection<UserBase> getAllUsers(UserType userType) throws BaseException {
		Connection connection = null;
		Statement prepared = null;
		UserBase userOfType = null;
		Collection<UserBase> users = new HashSet<>();
		String sqlstring = "SELECT * FROM " + userType.toString() + " ORDER BY id";
		ResultSet result = null;
		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.createStatement();
			result = prepared.executeQuery(sqlstring);
			while (result.next()) {

				userOfType = getNewUserInstance();
				userOfType.setUserName(result.getNString("name"));
				userOfType.setEmail(result.getNString("email"));
				userOfType.setId(result.getLong("id"));
				userOfType.setPassword(result.getNString("password"));
				users.add(userOfType);
				connection.commit();
			}
		} catch (SQLException | ClassNotFoundException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SQLExceptionHandler("the Connection Commit has RollBack.", SQLExceptionHandler.COMMIT);
			}
			throw new BaseException(e.getMessage());
		} finally {
			try {
				if(!result.isClosed()){
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
		return users;
	}

}
