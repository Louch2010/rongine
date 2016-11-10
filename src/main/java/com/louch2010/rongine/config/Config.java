package com.louch2010.rongine.config;

import java.util.List;

public class Config {
	// 应用信息
	private ApplicationConfig application;
	// 服务协议
	private ProtocolConfig protocol;
	// 全局超时设置
	private long globalTimeout;
	// 分布式注册中心
	private List<DistributedRegisterConfig> distributedRegisters;

	public ApplicationConfig getApplication() {
		return application;
	}

	public void setApplication(ApplicationConfig application) {
		this.application = application;
	}

	public ProtocolConfig getProtocol() {
		return protocol;
	}

	public void setProtocol(ProtocolConfig protocol) {
		this.protocol = protocol;
	}

	public long getGlobalTimeout() {
		return globalTimeout;
	}

	public void setGlobalTimeout(long globalTimeout) {
		this.globalTimeout = globalTimeout;
	}

	public List<DistributedRegisterConfig> getDistributedRegisters() {
		return distributedRegisters;
	}

	public void setDistributedRegisters(
			List<DistributedRegisterConfig> distributedRegisters) {
		this.distributedRegisters = distributedRegisters;
	}

}
