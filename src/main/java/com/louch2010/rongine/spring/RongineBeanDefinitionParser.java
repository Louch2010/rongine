package com.louch2010.rongine.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.louch2010.rongine.config.ApplicationConfig;
import com.louch2010.rongine.config.ProtocolConfig;

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
		MutablePropertyValues property = beanDefinition.getPropertyValues();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        //设置ID
        String id = getId(element, context);
        if(!StringUtils.isEmpty(id)){
        	property.add("id", id);
        }
        //针对不同的标签做相应的处理
        if(ApplicationConfig.class.equals(beanClass)){
        	property.add("name", element.getAttribute("name"));
        }else if(ProtocolConfig.class.equals(beanDefinition)){
        	
        }else{
        	
        }
		return beanDefinition;
	}
	
	/**
	  *description : 获取ID
	  *@param      : @param element
	  *@param      : @param context
	  *@param      : @return
	  *@return     : String
	  *modified    : 1、2016年11月24日 由 luocihang 创建 	   
	  */ 
	private String getId(Element element, ParserContext context){
		String id = element.getAttribute("id");
		//生成唯一id
		if(StringUtils.isEmpty(id) && required){
			id = element.getAttribute("name");
			if(StringUtils.isEmpty(id)){
				id = beanClass.getName();
			}
			int counter = 2;
            while(context.getRegistry().containsBeanDefinition(id)) {
                id = id + (counter ++);
            }
		}
		//判断是否有重复id
		if(!StringUtils.isEmpty(id) && context.getRegistry().containsBeanDefinition(id)){
			throw new IllegalStateException("Duplicate spring bean id " + id);
		}
		return id;
	}
}
