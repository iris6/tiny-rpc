package com.iris.tiny.rpc.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RpcContext {
    //TODO how can I get props as a provider? tip:ThreadLocal
    public static Map<String,Object> props = new HashMap<String, Object>();

    public static void addProp(String key ,Object value){
        props.put(key,value);
    }

    public static Object getProp(String key){
        return props.get(key);
    }

    public static Map<String,Object> getProps(){
       return Collections.unmodifiableMap(props);
    }
}
