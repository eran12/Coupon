package com.ee.ConnectionPool;

public class Configuration {

	public String DB_USER_NAME = "root";
	public String DB_PASSWORD = "MYSQL";
	public String DB_URL = "jdbc:mysql://127.0.0.1:3306/Coupon?useSSL=false";
	public String DB_DRIVER = "com.mysql.jdbc.Driver";
	public Integer DB_MAX_CONNECTIONS = 5;

	/**
	 * the constructor can`t be calling from outside this calls.
	 * this class will create only one time the Configuration method once the system, is running.</br>
	 * if any body will wont to change the configuration it will apply on every one who uses this class. 
	 */
	private Configuration() {
		
	}

	/**
	 * this method is static so it will create only one time.
	 * every class that will call the method {@link #getInstance()} will get the reference to this object. 
	 * 
	 */
	private static Configuration configuration = new Configuration();

	/**
	 * the method will return the object that create in the method {@link #configuration}.</br>
	 * the method should call from the {@link JdbcConnectionPool} .
	 * 
	 * @return Configuration Object. with the prepared instance.
	 */
	public static Configuration getInstance() {
		return configuration;
	}
}
