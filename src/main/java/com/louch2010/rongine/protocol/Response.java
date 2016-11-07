package com.louch2010.rongine.protocol;

import java.io.Serializable;

public class Response implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String requestId;
	private String code;
	private Throwable exception;
	private Object returnValue;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	@Override
	public String toString() {
		return "Response [requestId=" + requestId + ", code=" + code
				+ ", exception=" + exception + ", returnValue=" + returnValue
				+ "]";
	}

}
