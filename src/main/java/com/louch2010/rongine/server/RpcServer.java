package com.louch2010.rongine.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.louch2010.rongine.constants.Constant;
import com.louch2010.rongine.invoker.ServerInvoker;
import com.louch2010.rongine.protocol.ProtocolDispatcher;
import com.louch2010.rongine.protocol.Request;
import com.louch2010.rongine.protocol.Response;
import com.louch2010.rongine.register.RegisterBean;
import com.louch2010.rongine.register.ServerRegisterCenter;

public class RpcServer {
	private ServerRegisterCenter register;
	
	public RpcServer(int port, ServerRegisterCenter register){
		this.register = register;
		init(port);
	}
	
	public RpcServer(ServerRegisterCenter register){
		this(1334, register);
	}
	
	public void init(int port){
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			while(true){
				Socket socket = server.accept();
				InputStream input = socket.getInputStream();
				OutputStream output = socket.getOutputStream();
				//解析请求
				Request request = ProtocolDispatcher.parseJavaSerializableProtocol(input);
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
				ObjectOutputStream oos = new ObjectOutputStream(output);
				oos.writeObject(response);
				oos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(server != null){
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
