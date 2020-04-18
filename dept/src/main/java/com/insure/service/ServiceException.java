package com.insure.service;

/**
 * 自定义服务层异常类
 *  
 */
public class ServiceException extends Exception {
	
	 
	private static final long serialVersionUID = 5717888100550404100L;

	public ServiceException(String str){
		super(str);
	}

	public ServiceException() {
		super();
	}

	public ServiceException(String str, Throwable t) {
		super(str, t);
	}

	public ServiceException(Throwable t) {
		super(t);
	}
}
