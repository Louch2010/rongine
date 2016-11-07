package com.louch2010.rongine.test.server;

import com.louch2010.rongine.register.ServerRegisterCenter;
import com.louch2010.rongine.server.RpcServer;
import com.louch2010.rongine.test.HelloService;
import com.louch2010.rongine.test.HelloServiceImpl;

public class TestServer {
	public static void main(String[] args) {
		HelloService hello = new HelloServiceImpl();
		ServerRegisterCenter register = new ServerRegisterCenter();
		register.register(hello);
		new RpcServer(register);
	}
}
