package com.ee.Exception;

public class SQLExceptionHandler extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public static final int COMMIT = 1;
	public static final int RESULT_SET = 2;
	public static final int PREPERD_STATMENT = 3;
	public static final int SQLDRIVER_CLASS_NOT_FOUND = 4;
	
	public SQLExceptionHandler(String message){
		super(message);
	}
	
	public SQLExceptionHandler(String message, int errorCode){
		super(message,errorCode);
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "SQLExceptionHandler [toString()=" + super.toString() + "]";
	}
}
