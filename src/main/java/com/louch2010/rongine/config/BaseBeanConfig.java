package com.louch2010.rongine.config;

import java.util.HashMap;
import java.util.Map;

public class BaseBeanConfig {
	//唯一标识
	private String id;
	//接口名，即接口包的全路径
	private String interfaceName;
	//版本号
	private String version;
	//超时时间
	private Long timeout;
	//方法集合
	private Map<String, MethodConfig> methods= new HashMap<String, MethodConfig>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public Map<String, MethodConfig> getMethods() {
		return methods;
	}

	public void setMethods(Map<String, MethodConfig> methods) {
		this.methods = methods;
	}
}
