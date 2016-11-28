package com.louch2010.rongine.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.louch2010.rongine.config.ApplicationConfig;
import com.louch2010.rongine.config.RegisterConfig;
import com.louch2010.rongine.config.ProtocolConfig;
import com.louch2010.rongine.config.ReferenceConfig;
import com.louch2010.rongine.config.ServiceConfig;

public class RongineNamespaceHandler extends NamespaceHandlerSupport{

	public void init() {
		 registerBeanDefinitionParser("application", new RongineBeanDefinitionParser(ApplicationConfig.class, true));
		 registerBeanDefinitionParser("registry", new RongineBeanDefinitionParser(RegisterConfig.class, true));
		 registerBeanDefinitionParser("protocol", new RongineBeanDefinitionParser(ProtocolConfig.class, true));
		 registerBeanDefinitionParser("service", new RongineBeanDefinitionParser(ServiceConfig.class, true));
	     registerBeanDefinitionParser("reference", new RongineBeanDefinitionParser(ReferenceConfig.class, true));
	}
}
