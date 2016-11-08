package com.louch2010.rongine.invoker;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.louch2010.rongine.client.RpcClientHandler;
import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.protocol.Response;

public class ServerInvoker {
	private static Log log = LogFactory.getLog(RpcClientHandler.class);
	
	public static Response invoke(Object obj, Method method, Object[] param, String requestId){
		Response response = new Response();
		response.setRequestId(requestId);
		try {
			Object returnValue = method.invoke(obj, param);
			response.setReturnValue(returnValue);
			response.setCode(Constant.INVOKE_CODE.SUCCESS);
		}catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("调用方法失败！", e);
			}
			response.setException(e);
			response.setCode(Constant.INVOKE_CODE.INVOKER_ERROR);
		}
		return response;
	}
}
