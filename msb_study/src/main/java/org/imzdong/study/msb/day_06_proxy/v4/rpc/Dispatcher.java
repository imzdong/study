package org.imzdong.study.msb.day_06_proxy.v4.rpc;

import java.util.concurrent.ConcurrentHashMap;

public class Dispatcher{

    private ConcurrentHashMap<String, Object> serverMap = new ConcurrentHashMap<>();

    public void register(String key, Object object){
        serverMap.put(key, object);
    }

    public Object get(String key){
        return serverMap.get(key);
    }
}
