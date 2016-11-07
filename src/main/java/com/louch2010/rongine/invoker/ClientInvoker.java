package com.louch2010.rongine.invoker;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.protocol.Request;
import com.louch2010.rongine.protocol.Response;
import com.louch2010.rongine.util.MethodUtil;

public class ClientInvoker {
	public static Object invoke(Class<?> clazz, Method method, Object[] params){
		//封装请求对象
		String uri = clazz.getName() + "#" + MethodUtil.getMethodSign(method);
		Request request = new Request();
		request.setParams(params);
		request.setUri(uri);
		//发送请求
		Response response = null;
		try {
			Socket socket = new Socket("127.0.0.1", 1334);
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(output);
			oos.writeObject(request);
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(input);
			response = (Response) ois.readObject();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response != null){
			System.out.println(response.toString());
			if(Constant.INVOKE_CODE.SUCCESS.equals(response.getCode())){
				return response.getReturnValue();
			}
			if(response.getException() != null){
				throw new RuntimeException(response.getException());
			}
		}
		return null;
	}
}
