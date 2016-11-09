package com.louch2010.rongine.invoker;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.louch2010.rongine.client.RpcClientHandler;
import com.louch2010.rongine.config.ClientConfig;
import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.protocol.Request;
import com.louch2010.rongine.protocol.Response;

public class ClientInvoker {
	
	private static Log log = LogFactory.getLog(ClientInvoker.class);
	
	private RpcClientHandler handler; 
	private EventLoopGroup group;
	private ChannelFuture future;
	private ClientConfig config;
	
	public ClientInvoker(ClientConfig config){
		this.handler = new RpcClientHandler();
		this.config = config;
		init();
	}
	
	public Object invoke(Class<?> clazz, String methodSign, Object[] params) throws Exception{
		//封装请求对象
		String uri = clazz.getName() + "." + methodSign;
		Request request = new Request();
		request.setParams(params);
		request.setUri(uri);
		request.setId(UUID.randomUUID().toString());
		Response response = handler.sendMessage(request, config);
		//解析响应内容
		if(Constant.INVOKE_CODE.SUCCESS.equals(response.getCode())){
			return response.getReturnValue();
		}
		if(response.getException() != null){
			throw new Exception(response.getException());
		}
		return null;
	}
	
	/**
	  *description : 初始化服务，启动与server的连接
	  *@param      : 
	  *@return     : void
	  *modified    : 1、2016年11月9日 下午3:06:15 由 luocihang 创建 	   
	  */ 
	private void init(){
		log.info("初始化服务，启动与server的连接...");
		this.group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
					ch.pipeline().addLast(new ObjectEncoder());
					ch.pipeline().addLast(handler);
				}
			});
			this.future = b.connect(config.getServerIp(), config.getServerPort()).sync();
		} catch (Exception e) {
			log.error("初始化服务失败！", e);
			stop();
			throw new RuntimeException("初始化服务失败！", e);
		}
		log.info("初始化服务完成！");
	}
	
	/**
	  *description : 停止服务，断开与server的连接
	  *@param      : 
	  *@return     : void
	  *modified    : 1、2016年11月9日 下午3:05:57 由 luocihang 创建 	   
	  */ 
	public void stop(){
		log.info("停止服务，断开与server的连接...");
		try {
			handler.closeChannel();
			//future.channel().closeFuture().sync();
			group.shutdownGracefully();
		} catch (Exception e) {
			log.error(e);
		} finally{			
			log.info("服务停止完成！");
		}
	}
}
