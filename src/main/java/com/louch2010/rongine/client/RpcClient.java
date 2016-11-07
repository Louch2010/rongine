package com.louch2010.rongine.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.louch2010.rongine.invoker.ClientInvoker;
import com.louch2010.rongine.util.MethodUtil;

public class RpcClient implements InvocationHandler{
	
	private Class<?> clazz;
	
	public RpcClient(Class<?> clazz){
		this.clazz = clazz;
	}
    
    public <T>T create(){
    	return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }
    
	/**
	  *description : 动态代理调用
	  *@param      : @param proxy
	  *@param      : @param method
	  *@param      : @param args
	  *@param      : @return
	  *@param      : @throws Throwable
	  *@see        : 
	  *modified    : 1、2016年11月7日 下午5:51:00 由 luocihang 创建 
	  *			   
	  */ 
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodSign = MethodUtil.getMethodSign(method);
		//如果是接口中的方法，则使用动态代理，否则直接调用
		Method[] methods = clazz.getMethods();
		for(Method m: methods){
			if(methodSign.equals(MethodUtil.getMethodSign(m))){
				return ClientInvoker.invoke(clazz, methodSign, args);
			}
		}
		throw new NoSuchMethodError(clazz.getName() + "." + methodSign);
	}
	
}
