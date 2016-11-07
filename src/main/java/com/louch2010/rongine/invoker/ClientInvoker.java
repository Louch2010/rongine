package com.louch2010.rongine.invoker;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.protocol.Request;
import com.louch2010.rongine.protocol.Response;

public class ClientInvoker {
	
	public static Object invoke(Class<?> clazz, String methodSign, Object[] params) throws Exception{
		//封装请求对象
		String uri = clazz.getName() + "." + methodSign;
		Request request = new Request();
		request.setParams(params);
		request.setUri(uri);
		request.setId(UUID.randomUUID().toString());
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
			throw e;
		}
		if(response != null){
			System.out.println(response.toString());
			if(Constant.INVOKE_CODE.SUCCESS.equals(response.getCode())){
				return response.getReturnValue();
			}
			if(response.getException() != null){
				throw new Exception(response.getException());
			}
		}
		return null;
	}
}
