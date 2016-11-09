package com.louch2010.rongine.test.server;

import com.louch2010.rongine.config.ServerConfig;
import com.louch2010.rongine.register.ServerRegisterCenter;
import com.louch2010.rongine.server.RpcServer;
import com.louch2010.rongine.test.HelloService;
import com.louch2010.rongine.test.HelloServiceImpl;
import com.louch2010.rongine.test.WorkerService;
import com.louch2010.rongine.test.WorkerServiceImpl;

public class TestServer {
	public static void main(String[] args) {
		HelloService hello = new HelloServiceImpl();
		WorkerService worker = new WorkerServiceImpl();
		//注册中心
		ServerRegisterCenter register = new ServerRegisterCenter();
		register.register(hello);
		register.register(worker);
		//服务端配置
		ServerConfig config = new ServerConfig();
		config.setPort(1334);
		new RpcServer(config, register);
	}
}
