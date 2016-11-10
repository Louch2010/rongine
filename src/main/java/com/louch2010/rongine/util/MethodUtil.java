package com.louch2010.rongine.util;

import java.lang.reflect.Method;

public class MethodUtil {
	/**
	  *description : 获取方法的唯一签名
	  *@param      : @param m
	  *@param      : @return
	  *@return     : String
	  *modified    : 1、2016年11月7日 由 luocihang 创建 	   
	  */ 
	public static String getMethodSign(Method m){
		StringBuffer uri = new StringBuffer(m.getName() + "(");
		Class<?>[] params = m.getParameterTypes();
		for (int i = 0; i < params.length; i++) {
			uri.append(params[i].getName());
			if(i != params.length - 1){
				uri.append(",");
			}
		}
		uri.append(")");
		return uri.toString();
	}
}
