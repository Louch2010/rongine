package com.louch2010.rongine.spring;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.louch2010.rongine.config.ApplicationConfig;
import com.louch2010.rongine.config.ClientConfig;
import com.louch2010.rongine.config.ProtocolConfig;
import com.louch2010.rongine.config.ReferenceConfig;
import com.louch2010.rongine.config.RegisterConfig;
import com.louch2010.rongine.config.ServerConfig;
import com.louch2010.rongine.config.ServiceConfig;
import com.louch2010.rongine.register.ClientRegisterCenter;
import com.louch2010.rongine.register.ServerRegisterCenter;
import com.louch2010.rongine.server.RpcServer;

/** 
  * @Description: 服务启动入口
  * @author: luocihang
  * @date: 2016年11月28日
  * @version: V1.0 
  * @see：
  */
public class RongineSpringInject implements ApplicationContextAware{

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		ApplicationConfig application = context.getBean(ApplicationConfig.class);
		ProtocolConfig protocol = context.getBean(ProtocolConfig.class);
		Map<String, RegisterConfig> registers = context.getBeansOfType(RegisterConfig.class);
		Map<String, ServiceConfig> services = context.getBeansOfType(ServiceConfig.class);
		Map<String, ReferenceConfig> references = context.getBeansOfType(ReferenceConfig.class);
		//服务提供者
		if(services != null && services.size() > 0){
			//封装配置
			ServerConfig config = new ServerConfig();
			config.setApplication(application);
			config.setProtocol(protocol);
			config.setRegisters(registers);
			config.setServices(services);
			//注册服务
			ServerRegisterCenter center = new ServerRegisterCenter();
			try {
				center.register(context, config);
			} catch (Exception e) {
				throw new BeansException("注册服务出错！", e) {};
			}
			//启动服务
			new RpcServer(config, center);
		}
		//服务消费者
		if(references != null && references.size() > 0){
			//封装配置
			ClientConfig config = new ClientConfig();
			config.setApplication(application);
			config.setProtocol(protocol);
			config.setRegisters(registers);
			config.setReferences(references);
			//注册代理
			ClientRegisterCenter center = new ClientRegisterCenter();
			try {
				center.register(context, config);
			} catch (Exception e) {
				throw new BeansException("注册代理出错！", e) {};
			}
		}
	}
}
