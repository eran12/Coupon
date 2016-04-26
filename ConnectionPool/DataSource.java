package com.ee.ConnectionPool;

import java.sql.SQLException;

import java.sql.Connection;

public class DataSource {

	static JdbcConnectionPool pool = new JdbcConnectionPool();
	
	/**
	 * this gets a connection from the array in the connection-pool.</br>
	 * it`s should called in a Static way.
	 * @return connection form a connection pool.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		Connection connection = pool.getConnectionFromPool();
		return connection;
	}

	/**
	 * gives back the connection to the array list of the collection-pool.</br>
	 * this should called in a Static way.
	 * @param connection
	 */
	public static void returnConnection(Connection connection) {
		pool.returnConnectionToPool(connection);
	}
}