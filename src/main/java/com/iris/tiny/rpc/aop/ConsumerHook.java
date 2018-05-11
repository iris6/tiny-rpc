package com.iris.tiny.rpc.aop;

import com.iris.tiny.rpc.model.RpcRequest;

public interface ConsumerHook {
    public void before(RpcRequest request);
    public void after(RpcRequest request);
}
