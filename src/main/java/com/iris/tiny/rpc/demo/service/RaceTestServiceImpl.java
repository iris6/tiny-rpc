package com.iris.tiny.rpc.demo.service;

import com.iris.tiny.rpc.context.RpcContext;

import java.util.HashMap;
import java.util.Map;

public class RaceTestServiceImpl implements RaceTestService{
    @Override
    public Map<String, Object> getMap() {
        Map<String,Object> newMap = new HashMap<String,Object>();
        newMap.put("race","rpc");
        if(RpcContext.getProps() != null );
        newMap.putAll(RpcContext.getProps());
        return newMap;
    }

    @Override
    public String getString() {
        return "this is a rpc framework";
    }

    @Override
    public RaceDO getDO() {
        return new RaceDO();
    }

    @Override
    public boolean longTimeMethod() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Integer throwException() throws RaceException{
        throw new RaceException("just a exception");
    }
}
