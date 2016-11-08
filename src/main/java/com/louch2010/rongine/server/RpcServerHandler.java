package com.louch2010.rongine.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.invoker.ServerInvoker;
import com.louch2010.rongine.protocol.Request;
import com.louch2010.rongine.protocol.Response;
import com.louch2010.rongine.register.RegisterBean;
import com.louch2010.rongine.register.ServerRegisterCenter;

public class RpcServerHandler extends ChannelInboundHandlerAdapter {
	private ServerRegisterCenter register;
	private Log log = LogFactory.getLog(RpcServerHandler.class);

	public RpcServerHandler(ServerRegisterCenter register) {
		this.register = register;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("接收到请求，请求内容为：" + msg);
		//解析请求
		Request request = (Request) msg;
		//获取注册的service
		RegisterBean bean = register.getRegisterBean(request.getUri());
		Response response;
		//处理请求
		if(bean == null){
			response = new Response();
			response.setRequestId(request.getId());
			response.setCode(Constant.INVOKE_CODE.NO_METHOD);
			response.setException(new RuntimeException("no method regist for " + request.getUri()));
		}else{
			response = ServerInvoker.invoke(bean.getObj(), bean.getMethod(), request.getParams(), request.getId());
		}
		//响应请求
		ctx.writeAndFlush(response);
		log.info("请求处理完成，响应内容为：" + response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(log.isErrorEnabled()){
			log.error(cause);
		}
		ctx.close();
	}
}
