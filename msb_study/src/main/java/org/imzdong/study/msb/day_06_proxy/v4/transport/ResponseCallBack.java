package org.imzdong.study.msb.day_06_proxy.v4.transport;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseCallBack{

    static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();

    public static void addCallBack(long requestID,CompletableFuture cb){
        mapping.putIfAbsent(requestID,cb);
    }
    public static void runCallBack(PackMsg msg){
        CompletableFuture cf = mapping.get(msg.getMyHeader().getRequestId());
        cf.complete(msg.getMyContent().getRes());
        removeCB(msg.getMyHeader().getRequestId());
    }

    private static void removeCB(long requestID) {
        mapping.remove(requestID);
    }
}
