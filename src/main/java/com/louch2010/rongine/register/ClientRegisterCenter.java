package com.louch2010.rongine.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.louch2010.rongine.config.ClientConfig;
import com.louch2010.rongine.config.ServerConfig;

public class ClientRegisterCenter {
	private Map<String, AddressBean> register = new ConcurrentHashMap<String, AddressBean>();
	private Log log = LogFactory.getLog(ClientRegisterCenter.class);
	private ServerConfig config;
	
	public AddressBean getAddressBean(String uri) {
		return register.get(uri);
	}
	
	public void register(ApplicationContext context, ClientConfig config) throws Exception{
		
	}
}
