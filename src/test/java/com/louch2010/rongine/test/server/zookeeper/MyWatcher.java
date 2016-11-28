package com.louch2010.rongine.test.server.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.louch2010.rongine.register.zk.ZooKeeperOperator;

public class MyWatcher implements Watcher{
	private ZooKeeperOperator zkoperator;
	
	public MyWatcher(ZooKeeperOperator zkoperator){
		this.zkoperator = zkoperator;
	}

	public void process(WatchedEvent event) {
		System.out.println("type:" + event.getType());;
		try {
			zkoperator.getChild("/rongine", this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
