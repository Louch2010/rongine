package com.louch2010.rongine.config;

import java.util.HashMap;
import java.util.Map;

public class Config {
	private Map<String, Long> timeoutMap = new HashMap<String, Long>();
	private long globalTimeout;

	public Map<String, Long> getTimeoutMap() {
		return timeoutMap;
	}

	public void setTimeoutMap(Map<String, Long> timeoutMap) {
		this.timeoutMap = timeoutMap;
	}
	
	public long getGlobalTimeout() {
		return globalTimeout;
	}

	public void setGlobalTimeout(long globalTimeout) {
		this.globalTimeout = globalTimeout;
	}

	public void setTimeout(String methodName, long timeout){
		timeoutMap.put(methodName, timeout);
	}
	
	public long getTimeout(String methodName){
		Long timeout = timeoutMap.get(methodName);
		return timeout == null ? globalTimeout : timeout;
	}
}
