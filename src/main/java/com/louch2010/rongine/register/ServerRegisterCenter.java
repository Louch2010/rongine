package com.louch2010.rongine.register;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.louch2010.rongine.config.RegisterConfig;
import com.louch2010.rongine.config.ServiceConfig;
import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.util.MethodUtil;

/**
 * @Description: 注册中心
 * @author: luocihang
 * @date: 2016年11月7日
 * @version: V1.0
 * @see：
 */
public class ServerRegisterCenter {
	private Map<String, RegisterBean> register = new ConcurrentHashMap<String, RegisterBean>();
	private Log log = LogFactory.getLog(ServerRegisterCenter.class);

	public RegisterBean getRegisterBean(String uri) {
		return register.get(uri);
	}
	
	public void register(Object obj){
		register(obj, obj.getClass());
	}
	
	public void register(Object obj, Class<?> clazz){
		//如果有父接口，则循环遍历
		Class<?>[] interfaces = clazz.getInterfaces();
		for(Class<?> c:interfaces){
			register(obj, c);
		}
		//非接口的clazz忽略
		if(!Modifier.isInterface(clazz.getModifiers())){
			return;
		}
		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			// 获取方法的唯一签名
			String uri = clazz.getName() + Constant.METHOD_SPLID_CHAR + MethodUtil.getMethodSign(m);
			// 注册
			RegisterBean bean = new RegisterBean();
			bean.setUri(uri);
			bean.setMethod(m);
			bean.setObj(obj);
			register.put(uri, bean);
			log.info("注册请求url：" + uri);
		}
	}
	
	/**
	  *description : 向注册中心注册服务
	  *@param      : @param context
	  *@param      : @param registers
	  *@param      : @param services
	  *@return     : void
	  *modified    : 1、2016年11月28日 由 luocihang 创建 	   
	  */ 
	public void register(ApplicationContext context, Map<String, RegisterConfig> registers, Map<String, ServiceConfig> services){
		//先在本机进行注册
		for(String id : services.keySet()){
			ServiceConfig service = services.get(id);
			Object bean = context.getBean(service.getRef());
			this.register(bean);
		}
		//向注册中心进行注册
		for(String key : registers.keySet()){
			RegisterConfig register = registers.get(key);
			String address = register.getAddress();
			String protocol = register.getProtocol();
			//ZK注册中心
			if(Constant.REGISTRY_PROTOCOL.ZOOKEEPER.equalsIgnoreCase(protocol)){
				
			}else if(Constant.REGISTRY_PROTOCOL.RONGINE.equalsIgnoreCase(protocol)){
				//RONGINE注册中心不做处理
			}
		}
	}
}
