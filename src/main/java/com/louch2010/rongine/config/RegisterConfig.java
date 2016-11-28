package com.louch2010.rongine.config;

/** 
  * @Description: 分布式注册中心
  * @author: luocihang
  * @date: 2016年11月10日
  * @version: V1.0 
  * @see：
  */
public class RegisterConfig {
	private String id;
	private String protocol;
	private String address;

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
