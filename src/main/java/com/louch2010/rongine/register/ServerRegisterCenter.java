package com.louch2010.rongine.register;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.louch2010.rongine.util.MethodUtil;

/**
 * @Description: 注册中心
 * @author: luocihang
 * @date: 2016年11月7日 下午3:00:10
 * @version: V1.0
 * @see：
 */
public class ServerRegisterCenter {
	private Map<String, RegisterBean> register = new ConcurrentHashMap<String, RegisterBean>();

	public RegisterBean getRegisterBean(String uri) {
		return register.get(uri);
	}

	public void register(Object obj) {
		Class<?> clazz = obj.getClass();
		doRegister(obj, clazz, clazz.getInterfaces()[0]);
	}
	
	private void doRegister(Object obj, Class<?> clazz, Class<?> name){
		//如果有父接口，则循环遍历
		Class<?>[] interfaces = clazz.getInterfaces();
		if(interfaces != null && interfaces.length > 0){
			for(Class<?> c:interfaces){					
				doRegister(obj, c, name);
			}
		}
		//非接口的clazz忽略
		if(!Modifier.isInterface(clazz.getModifiers())){
			return;
		}
		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			// 获取方法的唯一签名
			String uri = name.getName() + "." + MethodUtil.getMethodSign(m);
			// 注册
			RegisterBean bean = new RegisterBean();
			bean.setUri(uri);
			bean.setMethod(m);
			bean.setObj(obj);
			register.put(uri, bean);
			System.out.println("注册：" + uri);
		}
	}
}
