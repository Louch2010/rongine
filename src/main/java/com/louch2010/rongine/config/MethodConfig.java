package com.louch2010.rongine.config;

/**
 * @Description: 方法
 * @author: luocihang
 * @date: 2016年11月10日
 * @version: V1.0
 * @see：
 */
public class MethodConfig {
	private String id;
	// 方法名，即方法签名
	private String name;
	// 超时时间
	private Long timeout;
	//重试次数
	private int retries;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
