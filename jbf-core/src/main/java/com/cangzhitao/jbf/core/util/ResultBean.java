package com.cangzhitao.jbf.core.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

public class ResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1486318617937304992L;
	
	public static final String ERROR = "error";
	
	public static final String SUCCESS = "success";
	
	private int code = HttpServletResponse.SC_OK;

	private String status = SUCCESS;

	private String message;
	
	private Object data;
	
	public ResultBean(){
	}

	public ResultBean(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
