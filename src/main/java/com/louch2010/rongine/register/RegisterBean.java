package com.louch2010.rongine.register;

import java.lang.reflect.Method;

public class RegisterBean {
	private String uri;
	private Object obj;
	private Method method;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
