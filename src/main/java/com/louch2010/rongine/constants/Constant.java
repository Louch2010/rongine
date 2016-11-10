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
	
}
