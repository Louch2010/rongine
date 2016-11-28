package com.louch2010.rongine.util;

import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
  * @Description: IP工具
  * @author: luocihang
  * @date: 2016年11月28日
  * @version: V1.0 
  * @see：
  */
public class IpUtil {
	private static Log logger = LogFactory.getLog(IpUtil.class);
	
	public static String getLocalIp() {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			String ip = ia.getHostAddress();
			return ip;
		} catch (Exception e) {
			logger.error("获取ip失败！", e);
			return "127.0.0.1";
		}
	}
}
