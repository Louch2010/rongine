package com.louch2010.rongine.register;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
}
