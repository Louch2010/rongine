package com.louch2010.rongine.register.zk;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperOperator extends AbstractZooKeeper{
    /**
      *description : 创建持久化结点
      *@param      : @param path
      *@param      : @param data
      *@param      : @throws Exception
      *@return     : void
      *modified    : 1、2016年11月28日 由 luocihang 创建 	   
      */ 
    public void createPersistentNode(String path, String data) throws Exception{
    	this.zooKeeper.create(path, data.getBytes("UTF-8"), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT); 
    }
    /**
      *description : 创建会话级别结点
      *@param      : @param path
      *@param      : @param data
      *@param      : @throws Exception
      *@return     : void
      *modified    : 1、2016年11月28日 由 luocihang 创建 	   
      */ 
    public void createEphemeralNode(String path, String data) throws Exception{
    	this.zooKeeper.create(path, data.getBytes("UTF-8"), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL); 
    }
    /**
      *description : 获取节点信息
      *@param      : @param path
      *@param      : @return
      *@param      : @throws KeeperException
      *@param      : @throws InterruptedException
      *@return     : List<String>
      *modified    : 1、2016年11月28日 由 luocihang 创建 	   
      */ 
    public List<String> getChild(String path) throws KeeperException, InterruptedException{     
        try{
        	return this.zooKeeper.getChildren(path, false);
        }catch (NoNodeException e) {
            throw e;     
        }  
    }
    /**
      *description : 获取节点信息(带监视器)
      *@param      : @param path
      *@param      : @param watcher
      *@param      : @return
      *@param      : @throws KeeperException
      *@param      : @throws InterruptedException
      *@return     : List<String>
      *modified    : 1、2016年11月28日 由 luocihang 创建 	   
      */ 
    public List<String> getChild(String path, Watcher watcher) throws KeeperException, InterruptedException{
    	return this.zooKeeper.getChildren(path, watcher);
    }
    /**
      *description : 获取结点数据
      *@param      : @param path
      *@param      : @return
      *@param      : @throws Exception
      *@return     : String
      *modified    : 1、2016年11月28日 由 luocihang 创建 	   
      */ 
    public String getData(String path) throws Exception{
        return new String(this.zooKeeper.getData(path, false, null), "UTF-8");     
    }
    
    /**
      *description : 节点是否存在
      *@param      : @param path
      *@param      : @return
      *@param      : @throws Exception
      *@return     : boolean
      *modified    : 1、2016年11月28日 由 luocihang 创建 	   
      */ 
    public boolean exists(String path) throws Exception{
    	Stat stat = zooKeeper.exists(path, false);
    	return stat != null;
    }
}
