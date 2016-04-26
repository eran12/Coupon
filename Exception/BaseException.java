package com.ee.Exception;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class BaseException extends RuntimeException{
	
	private static String mes;
	public static final LogRecord log = new LogRecord(Level.ALL, mes);
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	
	public BaseException(String message) {
		setMes(message);
		log.setMessage(this.getClass().getName() + "\nmessage: "  + message);
		System.out.println("log: " + log.getMessage());
	}
	
	public BaseException(String message, int errorCode){
		setMes(message);
		log.setMessage(this.getClass().getName() + "\nmessage: "  + message + "\nError Code: " + errorCode);
		System.out.println("log: " + log.getMessage());
	}

	public BaseException(SQLException message) {
		String sqlString = message.getMessage();
		setMes(sqlString);
		log.setMessage(this.getClass().getName() + "\nmessage: "  + sqlString );
		System.out.println("log: " + log.getMessage());
	}

	public BaseException(ClassNotFoundException message){
		String classNotFound = message.getMessage();
		setMes(classNotFound);
		log.setMessage(this.getClass().getName() + "\nmessage: "  + classNotFound );
		System.out.println("log: " + log.getMessage());
	}
	
	public BaseException(InterruptedException message){
		String InterruptedException = message.getMessage();
		setMes(InterruptedException);
		log.setMessage(this.getClass().getName() + "\nmessage: "  + InterruptedException);
		System.out.println("log: " + log.getMessage());
	}
	
	public String getMes() {
		return BaseException.mes;
	}
	
	public void setMes(String mes) {
		BaseException.mes = mes;
	}
	
	@Override
	public String toString() {
		return  getMes();
	}
}
