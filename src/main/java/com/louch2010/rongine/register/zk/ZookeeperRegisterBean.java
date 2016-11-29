package com.louch2010.rongine.register.zk;

import java.util.HashMap;
import java.util.Map;

import com.louch2010.rongine.config.MethodConfig;
import com.louch2010.rongine.config.ProtocolConfig;
import com.louch2010.rongine.config.ServiceConfig;

public class ZookeeperRegisterBean {
	private String uri;
	private ProtocolConfig protocolConfig;
	private ServiceConfig serviceConfig;
	private Map<String, MethodConfig> method = new HashMap<String, MethodConfig>();

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<String, MethodConfig> getMethod() {
		return method;
	}

	public void setMethod(Map<String, MethodConfig> method) {
		this.method = method;
	}
	
	public void addMethod(String name, MethodConfig config){
		method.put(name, config);
	}

	public ProtocolConfig getProtocolConfig() {
		return protocolConfig;
	}

	public void setProtocolConfig(ProtocolConfig protocolConfig) {
		this.protocolConfig = protocolConfig;
	}

	public ServiceConfig getServiceConfig() {
		return serviceConfig;
	}

	public void setServiceConfig(ServiceConfig serviceConfig) {
		this.serviceConfig = serviceConfig;
	}
	
}
