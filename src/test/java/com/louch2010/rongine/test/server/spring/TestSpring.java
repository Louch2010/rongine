package com.louch2010.rongine.test.server.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.louch2010.rongine.test.WorkerService;

public class TestSpring {
	public static void main(String[] args) {
		ApplicationContext application = new ClassPathXmlApplicationContext("spring.xml");
		WorkerService worker = (WorkerService) application.getBean("workerService");
		System.out.println(worker.doWork("tom"));
	}
}
