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

import com.louch2010.rongine.client.RpcClientHandler;
import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.protocol.Request;
import com.louch2010.rongine.protocol.Response;

public class ClientInvoker {
	
	public static Object invoke(Class<?> clazz, String methodSign, Object[] params) throws Exception{
		//封装请求对象
		String uri = clazz.getName() + "." + methodSign;
		final Request request = new Request();
		request.setParams(params);
		request.setUri(uri);
		request.setId(UUID.randomUUID().toString());
		final Response response = new Response();
		//发送
		EventLoopGroup group = new NioEventLoopGroup();
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
					ch.pipeline().addLast(new RpcClientHandler(request, response));
				}
			});
			ChannelFuture f = b.connect("127.0.0.1", 1334).sync();
			f.await();
			//f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			group.shutdownGracefully();
		}
		//解析响应内容
		if(Constant.INVOKE_CODE.SUCCESS.equals(response.getCode())){
			return response.getReturnValue();
		}
		if(response.getException() != null){
			throw new Exception(response.getException());
		}
		return null;
	}
}
