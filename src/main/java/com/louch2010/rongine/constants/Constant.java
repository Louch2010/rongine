package com.louch2010.rongine.constants;

public interface Constant {
	
	String METHOD_SPLID_CHAR = "#";
	/** 
	  * @Description: 调用错误码
	  * @author: luocihang
	  * @date: 2016年11月10日
	  * @version: V1.0 
	  * @see：
	  */
	public interface INVOKE_CODE {
		String SUCCESS = "SUCCESS";
		String INVOKER_ERROR = "ERROR";
		String NO_METHOD = "NO_METHOD";
		String INVOKER_TIMEOUT = "INVOKER_TIMEOUT";
		String CONNECT_CLOSE = "CONNECT_CLOSE";
	}
	
	/** 
	  * @Description: 注册中心协议
	  * @author: luocihang
	  * @date: 2016年11月28日
	  * @version: V1.0 
	  * @see：
	  */
	public interface REGISTRY_PROTOCOL{
		String ZOOKEEPER = "zookeeper";
		String RONGINE = "rongine";
	}
	
	/** 
	  * @Description: zk信息
	  * @author: luocihang
	  * @date: 2016年11月28日
	  * @version: V1.0 
	  * @see：
	  */
	public interface ZOOKEEPER_INFO{
		String BASE_NODE = "/rongine";
		String BASE_NODE_INFO = "rongine base node";
		String PROVIDERS_NODE = "/provider";
		String PROVIDERS_NODE_INFO = "rongine provider info";
		String CONSUMERS_NODE = "/consumer";
		String CONSUMERS_NODE_INFO = "rongine consumer info";
	}
}
