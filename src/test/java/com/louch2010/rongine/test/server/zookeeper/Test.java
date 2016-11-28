package com.louch2010.rongine.test.server.zookeeper;

import java.util.List;

import com.louch2010.rongine.register.zk.ZooKeeperOperator;

public class Test {
	public static void main(String[] args) {
		try {
			ZooKeeperOperator zkoperator = new ZooKeeperOperator();
			zkoperator.connect("172.25.47.43:2181");
			//zkoperator.createPersistentNode("/rongine", "this is rongine");
			//zkoperator.createPersistentNode("/rongine/test1", "this is test 1");
			zkoperator.createEphemeralNode("/rongine/test4", "this is test 4");
			List<String> list = zkoperator.getChild("/rongine");
			for(String node:list){
				System.out.println(node);
			}
			zkoperator.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
