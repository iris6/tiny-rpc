package com.iris.tiny.rpc.demo.service;

import com.iris.tiny.rpc.aop.ConsumerHook;
import com.iris.tiny.rpc.context.RpcContext;
import com.iris.tiny.rpc.model.RpcRequest;

public class RaceConsumerHook implements ConsumerHook{
    @Override
    public void before(RpcRequest request) {
        RpcContext.addProp("hook key","this is pass by hook");
    }

    @Override
    public void after(RpcRequest request) {
        System.out.println("I have finished Rpc calling.");
    }
}
