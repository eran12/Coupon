package com.ee.Exception;

public class IllegalPasswordException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	public static final int USER_BEAN = 1;
	public static final int COMPANY_BEAN = 2;
	public static final int ADMIN_BEAN = 3;
	public static final int USER_DBDAO = 4;
	public static final int COMPANY_DBDAO = 5;
	public static final int ASMIN_DBDAO = 6;
	
	
	public IllegalPasswordException(String message){
		super(message);
	}

	public IllegalPasswordException(String message, int errorCode){
		super(message, errorCode);
	}
}
