package com.louch2010.rongine.invoker;

import java.lang.reflect.Method;

import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.protocol.Response;

public class ServerInvoker {
	
	public static Response invoke(Object obj, Method method, Object[] param, String requestId){
		Response response = new Response();
		response.setRequestId(requestId);
		try {
			Object returnValue = method.invoke(obj, param);
			response.setReturnValue(returnValue);
			response.setCode(Constant.INVOKE_CODE.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			response.setException(e);
			response.setCode(Constant.INVOKE_CODE.INVOKER_ERROR);
		}
		return response;
	}
}
