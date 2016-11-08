package com.louch2010.rongine.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.louch2010.rongine.protocol.Request;
import com.louch2010.rongine.protocol.Response;

public class RpcClientHandler extends ChannelInboundHandlerAdapter{
	private Log log = LogFactory.getLog(RpcClientHandler.class);
	
	private Request request;
	private Response response;
	
	public RpcClientHandler(Request request, Response response){
		this.request = request;
		this.response = response;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("发送请求，请求内容为：" + request);
		//发送请求内容
		ctx.writeAndFlush(request);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("接收到请求响应，响应内容为：" + msg);
		//解析响应内容
		Response resp = (Response) msg;
		response.setCode(resp.getCode());
		response.setException(resp.getException());
		response.setRequestId(resp.getRequestId());
		response.setReturnValue(resp.getRequestId());
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
