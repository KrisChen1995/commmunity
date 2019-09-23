package com.chenfei.community.exception;

public class CustomizeException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message ; 
	private Integer code ;
	
	public CustomizeException (ICustomizeErrorCode errorCode) {
		this.message = errorCode.getMessage();
		this.code = errorCode.getCode();
	}
	
	
	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code ;
	}
}
