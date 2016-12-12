package com.louch2010.rongine.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.louch2010.rongine.client.RpcClient;
import com.louch2010.rongine.config.ClientConfig;
import com.louch2010.rongine.config.ReferenceConfig;
import com.louch2010.rongine.config.RegisterConfig;

public class ClientRegisterCenter {
	private Map<String, AddressBean> register = new ConcurrentHashMap<String, AddressBean>();
	private Log log = LogFactory.getLog(ClientRegisterCenter.class);
	
	public AddressBean getAddressBean(String uri) {
		return register.get(uri);
	}
	
	public void register(ApplicationContext context, ClientConfig config) throws Exception{
		Map<String, RegisterConfig> registers = config.getRegisters();
		Map<String, ReferenceConfig> references = config.getReferences();
		for(String key:references.keySet()){
			this.initConfig(config, registers);
			ReferenceConfig reference = references.get(key);
			//生成代理
			Class<?> clazz = Class.forName(reference.getInterfaceName());
			String id = reference.getId();
			RpcClient client = new RpcClient(config);
			Object service = client.create(clazz);
			//注入到spring容器
			DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
			factory.registerSingleton(id, service);
		}
	}
	
	/**
	  *description : 初始化配置
	  *@param      : @param config
	  *@return     : void
	  *modified    : 1、2016年12月12日 由 luocihang 创建 	   
	  */ 
	private void initConfig(ClientConfig config, Map<String, RegisterConfig> registers){
		config.setServerIp("127.0.0.1");
		config.setServerPort(20880);
	}
}
