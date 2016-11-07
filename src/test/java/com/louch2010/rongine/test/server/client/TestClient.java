package com.louch2010.rongine.test.server.client;

import java.lang.reflect.Method;

import com.louch2010.rongine.invoker.ClientInvoker;
import com.louch2010.rongine.test.HelloService;

public class TestClient {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		ClientInvoker invoker = new ClientInvoker();
		Object[] params = new Object[]{"罗词航"};
		Method method = HelloService.class.getMethod("sayHello", String.class);
		Object result = invoker.invoke(HelloService.class, method, params);
		System.out.println("响应：" + result);
	}
}
