package com.louch2010.rongine.config;

/** 
  * @Description: 服务提供者声明
  * @author: luocihang
  * @date: 2016年11月23日
  * @version: V1.0 
  * @see：
  */
public class ServiceConfig extends BaseBeanConfig{
	//实现类的引用
	private String ref;

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
	
}
