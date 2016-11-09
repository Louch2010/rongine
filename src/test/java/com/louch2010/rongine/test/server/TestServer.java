package com.louch2010.rongine.test.server;

import com.louch2010.rongine.config.ServerConfig;
import com.louch2010.rongine.register.ServerRegisterCenter;
import com.louch2010.rongine.server.RpcServer;
import com.louch2010.rongine.test.HelloService;
import com.louch2010.rongine.test.HelloServiceImpl;

public class TestServer {
	public static void main(String[] args) {
		HelloService hello = new HelloServiceImpl();
		//注册中心
		ServerRegisterCenter register = new ServerRegisterCenter();
		register.register(hello);
		//服务端配置
		ServerConfig config = new ServerConfig();
		config.setPort(1334);
		new RpcServer(config, register);
	}
}
