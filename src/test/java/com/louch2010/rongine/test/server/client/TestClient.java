package com.louch2010.rongine.test.server.client;

import java.util.Date;

import com.louch2010.rongine.client.RpcClient;
import com.louch2010.rongine.config.ClientConfig;
import com.louch2010.rongine.test.HelloService;
import com.louch2010.rongine.test.WorkerService;

public class TestClient {
	public static void main(String[] args) throws Exception{
		ClientConfig config = new ClientConfig();
		config.setServerIp("127.0.0.1");
		config.setServerPort(1334);
		config.setGlobalTimeout(1000);
		RpcClient client = new RpcClient(config);
		HelloService service = client.create(HelloService.class);
		WorkerService worker = client.create(WorkerService.class);
		/*for (int i = 0; i < 100; i++) {
			Worker worker = new Worker(i, service);
			new Thread(worker).start();
		}*/
		System.out.println("响应：" + service.talk("小明"));
		System.out.println("响应：" + worker.doWork("小李"));
		client.stop();
	}
	
}

class Worker implements Runnable{
	private int i;
	private HelloService service;
	public Worker(int i, HelloService service){
		this.i = i;
		this.service = service;
	}
	public void run() {
		String msg = "Tom" + i;
		String result = service.sayHello(msg, new Date());
		System.out.println(msg + " 响应：" + result);
	}
}
