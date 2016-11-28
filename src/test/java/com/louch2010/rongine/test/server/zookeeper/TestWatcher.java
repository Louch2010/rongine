package com.louch2010.rongine.test.server.zookeeper;

import java.util.List;

import com.louch2010.rongine.register.zk.ZooKeeperOperator;

public class TestWatcher {
	public static void main(String[] args) throws Exception{
		ZooKeeperOperator zkoperator = new ZooKeeperOperator();
		zkoperator.connect("172.25.47.43:2181");
		List<String> list =zkoperator.getChild("/rongine", new MyWatcher(zkoperator));
		for(String node:list){
			System.out.println(node);
		}
		new Thread(new Runnable() {
			public void run() {
				while(true){}
			}
		}).start();
	}
}
