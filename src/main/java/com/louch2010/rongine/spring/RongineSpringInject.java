package com.louch2010.rongine.spring;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.louch2010.rongine.config.ApplicationConfig;
import com.louch2010.rongine.config.ProtocolConfig;
import com.louch2010.rongine.config.RegisterConfig;
import com.louch2010.rongine.config.ServerConfig;
import com.louch2010.rongine.config.ServiceConfig;
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
		//封装配置
		ServerConfig config = new ServerConfig();
		config.setApplication(application);
		config.setProtocol(protocol);
		config.setRegisters(registers);
		//注册服务
		ServerRegisterCenter center = new ServerRegisterCenter();
		center.register(context, registers, services);
		//启动服务
		new RpcServer(config, center);
	}
}
