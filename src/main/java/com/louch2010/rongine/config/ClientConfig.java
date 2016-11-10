package com.louch2010.rongine.config;

import java.util.HashMap;
import java.util.Map;

import com.louch2010.rongine.constants.Constant;

public class ClientConfig extends Config {
	private Map<String, ReferenceConfig> references = new HashMap<String, ReferenceConfig>();
	private String serverIp;
	private int serverPort;

	public Map<String, ReferenceConfig> getReferences() {
		return references;
	}

	public void setReferences(Map<String, ReferenceConfig> references) {
		this.references = references;
	}
	
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	  *description : 获取超时时间
	  *@param      : @param url
	  *@param      : @return
	  *@return     : long
	  *modified    : 1、2016年11月10日 由 luocihang 创建 	   
	  */ 
	public long getTimeout(String url){
		String[] names = url.split(Constant.METHOD_SPLID_CHAR);
		ReferenceConfig reference = references.get(names[0]);
		if(reference == null){
			return super.getGlobalTimeout();
		}
		MethodConfig method = reference.getMethods().get(names[1]);
		//尝试从method中获取
		if(method.getTimeout() != null){
			return method.getTimeout();
		}
		//尝试从reference中获取
		if(reference.getTimeout() != null){
			return reference.getTimeout();
		}
		return super.getGlobalTimeout();
	}
}
