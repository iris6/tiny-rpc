package com.iris.tiny.rpc.zk.impl;

import com.iris.tiny.rpc.model.ZkData;
import com.iris.tiny.rpc.tool.Tool;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class StateListenerImpl implements Watcher{

    @Override
    public void process(WatchedEvent event) {
        ZkData data=new ZkData();
        try {
            data= Tool.deserialize(ZkClientImpl.zk.getData(event.getPath(), new StateListenerImpl(), null), ZkData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(event.getType()== Event.EventType.NodeDataChanged){
            ZkClientImpl.changeService(data);
            //System.out.println("zNode节点数据发生改变");
        }
        else if(event.getType()==Event.EventType.NodeDeleted){
            ZkClientImpl.deleteService(data);
            //System.out.println("zNode节点数据被删除");
        }
        else{
            //System.out.println("zNode发生改变");
        }
    }
}
