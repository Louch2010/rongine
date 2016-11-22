package com.louch2010.rongine.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class RongineBeanDefinitionParser implements BeanDefinitionParser{
	
	private static final Log logger = LogFactory.getLog(RongineBeanDefinitionParser.class);
    private final Class<?> beanClass;
    private final boolean required;
	
	public RongineBeanDefinitionParser (Class<?> beanClass, boolean required) {
		this.beanClass = beanClass;
		this.required = required;
	}

	public BeanDefinition parse(Element element, ParserContext context) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
		return null;
	}
}
