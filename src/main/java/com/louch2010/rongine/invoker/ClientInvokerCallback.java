package com.louch2010.rongine.invoker;

import java.util.concurrent.TimeoutException;

import com.louch2010.rongine.protocol.Response;

/** 
  * @Description: 回调
  * @author: luocihang
  * @date: 2016年11月9日 下午3:07:44
  * @version: V1.0 
  * @see：
  */
public class ClientInvokerCallback {
	private Response response;
	/**
	  *description : 获取响应信息，如果响应为空，则让线程等待
	  *@param      : @return
	  *@param      : @throws InterruptedException
	  *@return     : Response
	  *modified    : 1、2016年11月9日 下午3:07:58 由 luocihang 创建 	   
	  */ 
	public Response getResponse() throws InterruptedException{
		synchronized (this) {
			//回旋判断是否有结果了，没结果是释放锁，让当前线程处于等待状态
            while (response == null) {
                wait(); 
            }
        }
		return response;
	}
	
	/**
	  *description : 获取响应信息
	  *@param      : @param timeout
	  *@param      : @return
	  *@param      : @throws InterruptedException
	  *@return     : Response
	  *modified    : 1、2016年11月9日 下午4:44:23 由 luocihang 创建 	   
	  */ 
	public Response getResponse(long timeout) throws InterruptedException{
		//超时时间未设置
		if(timeout <= 0){
			return getResponse();
		}
		//如果设置了超时时间则进行相应的时候等待，如果response仍为空则返回异常
		synchronized (this) {
			if (response == null) {
	            wait(timeout);
	        }
			if(response == null){
				interrupt(new TimeoutException("获取响应结果失败！正时时间：" + timeout));
			}
		}
		return response;
	}
	
	/**
	  *description : 设置响应信息，唤醒处于等待的线程
	  *@param      : @param response
	  *@return     : void
	  *modified    : 1、2016年11月9日 下午3:09:25 由 luocihang 创建 	   
	  */ 
	public void setResponse(Response response) {
        this.response = response;
        //获取锁，因为前面wait()已经释放了callback的锁了
        synchronized (this) {
        	//唤醒处于等待的线程
            notifyAll(); 
        }
    }
	
	/**
	  *description : 中断响应
	  *@param      : 
	  *@return     : void
	  *modified    : 1、2016年11月9日 下午4:03:42 由 luocihang 创建 	   
	  */ 
	public void interrupt(Throwable e){
		Response response = new Response();
		response.setException(e);
		this.setResponse(response);
	}
}
