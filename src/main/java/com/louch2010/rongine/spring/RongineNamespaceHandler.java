package com.louch2010.rongine.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.louch2010.rongine.config.ApplicationConfig;
import com.louch2010.rongine.config.DistributedRegisterConfig;
import com.louch2010.rongine.config.ProtocolConfig;
import com.louch2010.rongine.config.ReferenceConfig;

public class RongineNamespaceHandler extends NamespaceHandlerSupport{

	public void init() {
		 registerBeanDefinitionParser("application", new RongineBeanDefinitionParser(ApplicationConfig.class, true));
		 registerBeanDefinitionParser("registry", new RongineBeanDefinitionParser(DistributedRegisterConfig.class, true));
		 registerBeanDefinitionParser("protocol", new RongineBeanDefinitionParser(ProtocolConfig.class, true));
		 /*registerBeanDefinitionParser("provider", new RongineBeanDefinitionParser(ProviderConfig.class, true));
	     registerBeanDefinitionParser("consumer", new RongineBeanDefinitionParser(ConsumerConfig.class, true));
	     registerBeanDefinitionParser("service", new RongineBeanDefinitionParser(ServiceBean.class, true));*/
	     registerBeanDefinitionParser("reference", new RongineBeanDefinitionParser(ReferenceConfig.class, false));
	}
}
