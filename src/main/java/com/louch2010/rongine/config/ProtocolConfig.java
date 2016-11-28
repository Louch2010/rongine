package com.louch2010.rongine.config;

/**
 * @Description:
 * @author: luocihang
 * @date: 2016年11月10日
 * @version: V1.0
 * @see：
 */
public class ProtocolConfig {
	private String id;
	//主机IP
	private String host;
	//端口号
	private int port;
	//协议名
	private String name;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
