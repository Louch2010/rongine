package com.louch2010.rongine.invoker;

import java.lang.reflect.Method;

import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.protocol.Response;

public class ServerInvoker {
	
	public static Response invoke(Object obj, Method method, Object[] param){
		Response resp = new Response();
		try {
			Object returnValue = method.invoke(obj, param);
			resp.setReturnValue(returnValue);
			resp.setCode(Constant.INVOKE_CODE.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			resp.setException(e);
			resp.setCode(Constant.INVOKE_CODE.INVOKER_ERROR);
		}
		return resp;
	}
}
