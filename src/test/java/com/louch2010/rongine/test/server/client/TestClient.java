package com.louch2010.rongine.test.server.client;

import java.util.Date;

import com.louch2010.rongine.client.RpcClient;
import com.louch2010.rongine.config.ClientConfig;
import com.louch2010.rongine.test.HelloService;

public class TestClient {
	public static void main(String[] args) throws Exception{
		ClientConfig config = new ClientConfig();
		config.setServerIp("127.0.0.1");
		config.setServerPort(1334);
		config.setGlobalTimeout(1);
		RpcClient client = new RpcClient(config);
		HelloService service = client.create(HelloService.class);
		String result = service.sayHello("Tom", new Date());
		System.out.println("响应：" + result);
		//client.stop();
		//Object result1 = service.talk("小明");
		//System.out.println("响应：" + result1);
	}
}
