package com.louch2010.rongine.test;

import java.util.Date;

public interface HelloService extends TalkService{
	public String sayHello(String name);
	public String sayHello(String name, Date time);
}
