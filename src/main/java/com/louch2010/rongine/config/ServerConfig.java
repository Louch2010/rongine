package com.louch2010.rongine.config;

import java.util.HashMap;
import java.util.Map;

public class ServerConfig extends Config{
	private Map<String, ServiceConfig> services = new HashMap<String, ServiceConfig>();

	public Map<String, ServiceConfig> getServices() {
		return services;
	}

	public void setServices(Map<String, ServiceConfig> services) {
		this.services = services;
	}
	
}
