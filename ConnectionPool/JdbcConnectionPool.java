package com.ee.ConnectionPool;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

public class JdbcConnectionPool {

	// attributes 
	// list to hold all the connections.
	List<Connection> availableConnections = new ArrayList<Connection>();

	// constructor
	public JdbcConnectionPool()
	{
		initializeConnectionPool();
	}

	/**
	 * check if the connection pool is get to the max.
	 * if the connection is smaller then what defined in the {@link Configuration} class,
	 * its create a new connection.
	 */
	private void initializeConnectionPool()
	{
		while(!checkIfConnectionPoolIsFull())
		{
			availableConnections.add(createNewConnectionForPool());
		}
	}

	/**
	 * check if the connection pool gets to full.
	 * 
	 * @return if connection pool is full return true.</br>
	 * if connection pool is not full return false.
	 */
	private synchronized boolean checkIfConnectionPoolIsFull()
	{
		final int MAX_POOL_SIZE = Configuration.getInstance().DB_MAX_CONNECTIONS;

		if(availableConnections.size() < MAX_POOL_SIZE)
		{
			return false;
		}

		return true;
	}

	/**
	 * Creating a connection
	 * @return if all the attributes in the {@link Configuration} class is right.</br>
	 * it will return a connection if one of the attributes is wrong it will return null.
	 */
	private Connection createNewConnectionForPool()
	{
		Configuration config = Configuration.getInstance();
		try {
			Class.forName(config.DB_DRIVER);
			Connection connection = (Connection) DriverManager.getConnection(
					config.DB_URL, config.DB_USER_NAME, config.DB_PASSWORD);
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * this handle the amount of connections in the pool.
	 * if the connection that open is bigger than 1 so it remove the connection from the pool.
	 * @return {@link Connection}
	 */
	public synchronized Connection getConnectionFromPool()
	{
		Connection connection = null;
		if(availableConnections.size() > 0)
		{
			connection = (Connection) availableConnections.get(0);
			availableConnections.remove(0);
		}
		return connection;
	}

	/**
	 * add the connection to the Array list.
	 * @param connection
	 */
	public synchronized void returnConnectionToPool(Connection connection)
	{
		availableConnections.add(connection);
	}

}


