package com.louch2010.rongine.register.zk;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.register.RegisterBean;

public class RongineServerZkHandler {
	private static Log log = LogFactory.getLog(RongineServerZkHandler.class);
	/**
	  *description : 处理服务端zk注册
	  *@param      : @param address
	  *@param      : @param beans
	  *@return     : void
	  *modified    : 1、2016年11月28日 由 luocihang 创建 	   
	 * @throws Exception 
	  */ 
	public static void handle(String address, Map<String, RegisterBean> beans) throws Exception{
		//协议处理
		String protocol = "zookeeper://";
		if(address.startsWith(protocol)){
			address = address.substring(protocol.length());
		}
		//bean转换
		Map<String, ZookeeperRegisterBean> serviceMap = getZookeeperRegisterBeanMap(beans);
		//多个zk地址进行处理
		String[] zks = address.split(";");
		ZooKeeperOperator zkoperator;
		for (int i = 0; i < zks.length; i++) {
			if(StringUtils.isEmpty(zks[i])){
				continue;
			}
			//连接zk
			try {
				zkoperator = new ZooKeeperOperator();
				zkoperator.connect(zks[i]);
			} catch (Exception e) {
				log.error("连接zookeeper出错，zk：" + zks[i], e);
				continue;
			}
			//创建根结点
			if(!zkoperator.exists(Constant.ZOOKEEPER_INFO.BASE_NODE)){
				zkoperator.createPersistentNode(Constant.ZOOKEEPER_INFO.BASE_NODE, Constant.ZOOKEEPER_INFO.BASE_NODE_INFO);
			}
			//注册服务
			for(String key : serviceMap.keySet()){
				ZookeeperRegisterBean bean = serviceMap.get(key);
				register(bean, zkoperator);
			}
		}
	}
	
	/**
	  *description : 注册方法
	  *@param      : @param bean
	  *@param      : @param zkoperator
	  *@param      : @throws Exception
	  *@return     : void
	  *modified    : 1、2016年11月28日 由 luocihang 创建 	   
	  */ 
	private static void register(ZookeeperRegisterBean bean, ZooKeeperOperator zkoperator) throws Exception{
		//没有服务结点则创建
		String serviceNode = Constant.ZOOKEEPER_INFO.BASE_NODE + "/" + bean.getUri();
		if(!zkoperator.exists(serviceNode)){
			zkoperator.createPersistentNode(serviceNode, "");
		}
		//创建providers结点
		String provider = serviceNode +  Constant.ZOOKEEPER_INFO.PROVIDERS_NODE;
		if(!zkoperator.exists(provider)){
			zkoperator.createPersistentNode(provider, Constant.ZOOKEEPER_INFO.PROVIDERS_NODE_INFO);
		}
		//节点信息
		String host = bean.getProtocolConfig().getHost() + ":" + bean.getProtocolConfig().getPort();
		String path = provider + "/" + host;
		if(!zkoperator.exists(path)){
			zkoperator.createEphemeralNode(path, "");//会话级别
		}
	}
	
	/**
	  *description : bean转换
	  *@param      : @param beans
	  *@param      : @return
	  *@return     : Map<String,ZookRegisterBean>
	  *modified    : 1、2016年11月29日 由 luocihang 创建 	   
	  */ 
	private static Map<String, ZookeeperRegisterBean> getZookeeperRegisterBeanMap(Map<String, RegisterBean> beans){
		Map<String, ZookeeperRegisterBean> map = new HashMap<String, ZookeeperRegisterBean>();
		for(String uri:beans.keySet()){
			RegisterBean bean = beans.get(uri);
			//将uri进行拆分
			String[] methodInfo = uri.split(Constant.METHOD_SPLID_CHAR);
			String serviceName = methodInfo[0];
			if(map.containsKey(serviceName)){
				map.get(serviceName).addMethod(methodInfo[1], bean.getMethodConfig());
			}else{
				ZookeeperRegisterBean zook = new ZookeeperRegisterBean();
				zook.setUri(serviceName);
				zook.addMethod(methodInfo[1], bean.getMethodConfig());
			}
		}
		return map;
	}
}
