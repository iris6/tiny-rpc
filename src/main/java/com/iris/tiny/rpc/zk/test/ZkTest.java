package com.iris.tiny.rpc.zk.test;

import com.iris.tiny.rpc.model.ZkData;
import com.iris.tiny.rpc.tool.Tool;
import com.iris.tiny.rpc.zk.ZkClient;
import com.iris.tiny.rpc.zk.impl.ZkClientImpl;
import org.apache.zookeeper.CreateMode;
import org.junit.Assert;
import org.junit.Test;

public class ZkTest {

    public ZkData init(){
        ZkData zkData = new ZkData();
        zkData.setIP("127.0.0.1");
        zkData.setPort("8080");
        zkData.setServiceName("iris");
        zkData.setServiceId("1234");
        return zkData;
    }

    @Test
    public void setDataTest()throws Exception{
        ZkClient zk=new ZkClientImpl("0.0.0.0:2181");
        Thread.currentThread().sleep(3000);
        Assert.assertEquals(true,zk.isConnected());
        ZkData zkData=init();
        zk.setData("/tiny", Tool.serialize(zkData),-1, CreateMode.EPHEMERAL);
        ZkClientImpl.addService(zk.getData("/tiny",-1,true));
        System.out.println("test :"+ZkClientImpl.serviceData);
        while (true);
    }

    @Test
    public void watchTest()throws Exception{
        ZkClient zk=new ZkClientImpl("0.0.0.0:2181");
        Thread.currentThread().sleep(3000);
        Assert.assertEquals(true,zk.isConnected());
        ZkData zkData=init();
        zkData.setServiceName("iris6");
        zk.setData("/tiny",Tool.serialize(zkData),-1, CreateMode.EPHEMERAL);
        System.out.println("test :"+ZkClientImpl.serviceData);
    }
}
