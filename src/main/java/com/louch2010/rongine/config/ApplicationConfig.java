package com.louch2010.rongine.config;

/** 
  * @Description: 应用信息
  * @author: luocihang
  * @date: 2016年11月10日
  * @version: V1.0 
  * @see：
  */
public class ApplicationConfig {
	private String Id;
	//应用名
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	@Override
	public String toString() {
		return "ApplicationConfig [name=" + name + "]";
	}
}
