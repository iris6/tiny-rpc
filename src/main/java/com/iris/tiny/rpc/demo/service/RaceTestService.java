package com.iris.tiny.rpc.demo.service;

import java.util.Map;

public interface RaceTestService {
    public Map<String,Object> getMap();
    public String getString();
    public RaceDO getDO();
    public boolean longTimeMethod();
    public Integer throwException() throws RaceException;
}
