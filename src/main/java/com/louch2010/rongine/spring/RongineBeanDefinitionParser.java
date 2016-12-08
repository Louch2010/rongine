package com.louch2010.rongine.spring;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.louch2010.rongine.config.ApplicationConfig;
import com.louch2010.rongine.config.MethodConfig;
import com.louch2010.rongine.config.ProtocolConfig;
import com.louch2010.rongine.config.ReferenceConfig;
import com.louch2010.rongine.config.RegisterConfig;
import com.louch2010.rongine.config.ServiceConfig;
import com.louch2010.rongine.util.IpUtil;

public class RongineBeanDefinitionParser implements BeanDefinitionParser {

	private final Class<?> beanClass;
	private final boolean required;

	public RongineBeanDefinitionParser(Class<?> beanClass, boolean required) {
		this.beanClass = beanClass;
		this.required = required;
	}
	
	public BeanDefinition parse(Element element, ParserContext context) {
		return this.parse(element, context, beanClass, required);
	}

	private BeanDefinition parse(Element element, ParserContext context, Class<?> beanClass, boolean required) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		MutablePropertyValues property = beanDefinition.getPropertyValues();
		beanDefinition.setBeanClass(beanClass);
		beanDefinition.setLazyInit(false);
		// 设置ID
		String id = getId(element, context, beanClass);
		if (!StringUtils.isEmpty(id)) {
			property.add("id", id);
		}
		// 针对不同的标签做相应的处理
		if (ApplicationConfig.class.equals(beanClass)) {
			property.add("name", element.getAttribute("name"));
		} else if (ProtocolConfig.class.equals(beanClass)) {
			property.add("name", element.getAttribute("name"));
			property.add("port", element.getAttribute("port"));
			String host = element.getAttribute("host");
			if(StringUtils.isEmpty(host)){
				property.add("host", IpUtil.getLocalIp());
			}else{				
				property.add("host", host);
			}
		} else if (RegisterConfig.class.equals(beanClass)) {
			property.add("protocol", element.getAttribute("protocol"));
			property.add("address", element.getAttribute("address"));
		} else if (ServiceConfig.class.equals(beanClass)) {
			property.add("ref", element.getAttribute("ref"));
			this.setCommonProperties(property, element);
			this.parseMethods(id, element.getChildNodes(), property, context);
		} else if (ReferenceConfig.class.equals(beanClass)) {
			this.setCommonProperties(property, element);
			this.parseMethods(id, element.getChildNodes(), property, context);
		}
		//将配置进行注册
		context.getRegistry().registerBeanDefinition(id + "_config", beanDefinition);
		return beanDefinition;
	}

	/**
	 * description : 获取ID
	 * 
	 * @param : @param element
	 * @param : @param context
	 * @param : @return
	 * @return : String modified : 1、2016年11月24日 由 luocihang 创建
	 */
	private String getId(Element element, ParserContext context, Class<?> beanClass) {
		String id = element.getAttribute("id");
		// 生成唯一id
		if (StringUtils.isEmpty(id) && required) {
			id = element.getAttribute("name");
			if (StringUtils.isEmpty(id)) {
				id = beanClass.getName();
			}
			int counter = 2;
			while (context.getRegistry().containsBeanDefinition(id)) {
				id = id + (counter++);
			}
		}
		// 判断是否有重复id
		if (!StringUtils.isEmpty(id) && context.getRegistry().containsBeanDefinition(id)) {
			throw new IllegalStateException("Duplicate spring bean id " + id);
		}
		return id;
	}

	/**
	 * description : 设置通用属性
	 * 
	 * @param : @param property
	 * @param : @param element
	 * @return : void modified : 1、2016年11月25日 由 luocihang 创建
	 */
	private void setCommonProperties(MutablePropertyValues property,
			Element element) {
		String interfaceName = element.getAttribute("interface");
		if (!StringUtils.isEmpty(interfaceName)) {
			property.add("interfaceName", interfaceName);
		}
		String version = element.getAttribute("version");
		if (!StringUtils.isEmpty(version)) {
			property.add("version", version);
		}
		String timeout = element.getAttribute("timeout");
		if (!StringUtils.isEmpty(version)) {
			property.add("timeout", Long.parseLong(timeout));
		}
		String retries = element.getAttribute("retries");
		if (!StringUtils.isEmpty(version)) {
			property.add("retries", Integer.parseInt(retries));
		}
	}

	/**
	  *description : 解析方法
	  *@param      : @param id
	  *@param      : @param nodeList
	  *@param      : @param property
	  *@param      : @param parserContext
	  *@return     : void
	  *modified    : 1、2016年11月25日 由 luocihang 创建 	   
	  */ 
	private void parseMethods(String id, NodeList nodeList, MutablePropertyValues property, ParserContext parserContext) {
		if(nodeList == null || nodeList.getLength() == 0){
			return;
		}
		ManagedMap<String, BeanDefinitionHolder> methods = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(!(node instanceof Element)){
				continue;
			}
			Element element = (Element) node;
			if ("method".equals(node.getNodeName()) || "method".equals(node.getLocalName())) {
				String methodName = element.getAttribute("name");
				if (StringUtils.isEmpty(methodName)) {
					throw new IllegalStateException("<rongine:method> name attribute == null");
				}
				if (methods == null) {
					methods = new ManagedMap<String, BeanDefinitionHolder>();
				}
				BeanDefinition methodBeanDefinition = this.parse(((Element) node), parserContext, MethodConfig.class, false);
				String name = id + "." + methodName;
				BeanDefinitionHolder methodBeanDefinitionHolder = new BeanDefinitionHolder(methodBeanDefinition, name);
				methods.put(name, methodBeanDefinitionHolder);
			}
		}
		if (methods != null) {
			property.addPropertyValue("methods", methods);
		}
	}
}
