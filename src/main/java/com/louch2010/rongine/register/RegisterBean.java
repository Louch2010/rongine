package com.louch2010.rongine.register;

import java.lang.reflect.Method;

import com.louch2010.rongine.config.MethodConfig;
import com.louch2010.rongine.config.ProtocolConfig;
import com.louch2010.rongine.config.ServiceConfig;

public class RegisterBean {
	private String uri;
	private Object obj;
	private Method method;
	private ServiceConfig serviceConfig;
	private MethodConfig methodConfig;
	private ProtocolConfig protocolConfig;

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

	public ServiceConfig getServiceConfig() {
		return serviceConfig;
	}

	public void setServiceConfig(ServiceConfig serviceConfig) {
		this.serviceConfig = serviceConfig;
	}

	public MethodConfig getMethodConfig() {
		return methodConfig;
	}

	public void setMethodConfig(MethodConfig methodConfig) {
		this.methodConfig = methodConfig;
	}

	public ProtocolConfig getProtocolConfig() {
		return protocolConfig;
	}

	public void setProtocolConfig(ProtocolConfig protocolConfig) {
		this.protocolConfig = protocolConfig;
	}
	
	
}
