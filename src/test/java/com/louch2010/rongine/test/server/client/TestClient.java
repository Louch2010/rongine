package com.louch2010.rongine.test.server.client;

import com.louch2010.rongine.client.RpcClient;
import com.louch2010.rongine.test.HelloService;

public class TestClient {
	public static void main(String[] args) throws Exception{
		RpcClient client = new RpcClient(HelloService.class);
		HelloService service = client.create();
		//String result = service.sayHello("Tom", new Date());
		Object result = service.talk("小明");
		System.out.println("响应：" + result);
	}
}
