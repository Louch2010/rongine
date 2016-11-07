package com.louch2010.rongine.protocol;

import java.io.InputStream;
import java.io.ObjectInputStream;

public class ProtocolDispatcher{
	
	public static Request parseHessianProtocol(InputStream input) {
		
		return null;
	}
	
	public static Request parseJavaSerializableProtocol(InputStream input) throws Exception{
		ObjectInputStream ois = new ObjectInputStream(input);
		Object req = ois.readObject();
		return (Request) req;
	}
}
