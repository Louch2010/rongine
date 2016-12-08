package com.louch2010.rongine.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;

import com.louch2010.rongine.client.RpcClient;
import com.louch2010.rongine.config.ClientConfig;
import com.louch2010.rongine.config.ReferenceConfig;
import com.louch2010.rongine.config.RegisterConfig;
import com.louch2010.rongine.config.ServerConfig;

public class ClientRegisterCenter {
	private Map<String, AddressBean> register = new ConcurrentHashMap<String, AddressBean>();
	private Log log = LogFactory.getLog(ClientRegisterCenter.class);
	private ServerConfig config;
	
	public AddressBean getAddressBean(String uri) {
		return register.get(uri);
	}
	
	public void register(ApplicationContext context, ClientConfig config) throws Exception{
		Map<String, RegisterConfig> registers = config.getRegisters();
		Map<String, ReferenceConfig> references = config.getReferences();
		for(String key:references.keySet()){
			ReferenceConfig reference = references.get(key);
			//生成代理
			Class<?> clazz = Class.forName(reference.getInterfaceName());
			String id = reference.getId();
			RpcClient client = new RpcClient(config);
			Object service = client.create(clazz);
			//注入到spring容器
			//BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition("xxxClassName");
		    //builder.addPropertyReference("refBean11", "refBean11");//value是String类型，其实就是beanName    
		    //builder.addPropertyValue(id, service);//value是object类型，与xml配置一样  
		    //this.beanFactory.registerBeanDefinition("myNewBean", builder.getBeanDefinition());
			
		}
	}
}
