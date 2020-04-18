package com.insure.bean;


public class ResultInfo {
	
	private Integer value;
	private Boolean isSuccess; 
	private String message;
	 
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	} 

	public ResultInfo(Boolean isSuccess, String message) {
		super();
		this.isSuccess = isSuccess;
		this.message = message;
	}

	public ResultInfo() {
		super();
		// TODO Auto-generated constructor stub
	} 
	
	
	public ResultInfo(Integer value, String message) {
		super();
		this.value = value;
		this.message = message;
	} 

	public ResultInfo(Integer value, Boolean isSuccess, String message) {
		super();
		this.value = value;
		this.isSuccess = isSuccess;
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResultInfo [value=" + value + ", isSuccess=" + isSuccess
				+ ", message=" + message + "]";
	}
	
	
	
}
