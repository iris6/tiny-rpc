package com.iris.tiny.rpc.netty;

import java.util.List;
import java.util.Map;

import com.iris.tiny.rpc.async.ResponseCallbackListener;
import com.iris.tiny.rpc.model.RpcRequest;

/**
 * 描述与服务器的连接
 *
 */
public interface RpcConnection {
	void init();
	void connect();
	void connect(String host,int port);
	Object Send(RpcRequest request, boolean async);
	void close();
	boolean isConnected();
	boolean isClosed();
	public boolean containsFuture(String key);
	public InvokeFuture<Object> removeFuture(String key);
	public void setResult(Object ret);
	public void setTimeOut(long timeout);
	public void setAsyncMethod(Map<String,ResponseCallbackListener> map);
	public List<InvokeFuture<Object>> getFutures(String method);
}
