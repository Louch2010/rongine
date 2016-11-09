package com.louch2010.rongine.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.louch2010.rongine.config.ClientConfig;
import com.louch2010.rongine.invoker.ClientInvokerCallback;
import com.louch2010.rongine.protocol.Request;
import com.louch2010.rongine.protocol.Response;

public class RpcClientHandler extends ChannelInboundHandlerAdapter{
	private Log log = LogFactory.getLog(RpcClientHandler.class);
	private Map<String, ClientInvokerCallback> callBackPool = new ConcurrentHashMap<String, ClientInvokerCallback>();
	private ChannelHandlerContext channelHandlerContext;
	
	/**
	  *description : 发送消息并获取响应结果
	  *@param      : @param request
	  *@param      : @return
	  *@param      : @throws InterruptedException
	  *@return     : Response
	  *modified    : 1、2016年11月9日 下午3:18:24 由 luocihang 创建 	   
	  */ 
	public Response sendMessage(Request request, ClientConfig config) throws InterruptedException{
		log.info("发送请求，请求内容为：" + request);
		if(channelHandlerContext == null){
			log.error("连接未初始化或已关闭， 不能发送消息！");
			throw new RuntimeException("连接未初始化或已关闭， 不能发送消息！");
		}
		ClientInvokerCallback callback = new ClientInvokerCallback();
		//将请求进行缓存
		callBackPool.put(request.getId(), callback);
		//发送请求内容
		channelHandlerContext.writeAndFlush(request);
		//获取响应内容，如果没有响应则会一直
		return callback.getResponse(config.getTimeout(request.getUri()));
	}
	
	/**
	  *description : 关闭channel
	  *@param      : 
	  *@return     : void
	  *modified    : 1、2016年11月9日 下午4:04:13 由 luocihang 创建 	   
	  */ 
	public void closeChannel(){
		//对于正在发送的请求进行关闭
		for (String id : callBackPool.keySet()) {
			callBackPool.get(id).interrupt(new RuntimeException("连接已关闭，无法发送请求！"));
		}
		channelHandlerContext = null;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//channel已经准备就绪，可供调用者发送消息
		this.channelHandlerContext = ctx;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("接收到请求响应，响应内容为：" + msg);
		//解析响应内容
		Response resp = (Response) msg;
		ClientInvokerCallback callback = callBackPool.get(resp.getRequestId());
		if(callback == null){
			log.error("原始连接不存在，响应处理失败，RequestId：" + resp.getRequestId());
			throw new IllegalAccessError("原始连接不存在，响应处理失败，RequestId：" + resp.getRequestId());
		}
		//将回调从缓存池中移除
		callBackPool.remove(callback);
		//执行回调
		callback.setResponse(resp);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error(cause);
		ctx.close();
	}
}
