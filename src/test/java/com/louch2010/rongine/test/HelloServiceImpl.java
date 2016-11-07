package com.louch2010.rongine.test;

import java.util.Date;

public class HelloServiceImpl implements HelloService{

	public String sayHello(String name) {
		return "hello, " + name + " ~!";
	}

	public String sayHello(String name, Date time) {
		return "你好, " + name + "，当前时间： " + time;
	}
}
