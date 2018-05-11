package com.iris.tiny.rpc.zk.impl;

import com.iris.tiny.rpc.model.ZkData;
import com.iris.tiny.rpc.tool.Tool;
import com.iris.tiny.rpc.zk.ZkClient;
import org.apache.zookeeper.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZkClientImpl implements ZkClient{

    public static ZooKeeper zk;

    public static String url;

    public static int sessionTime=2000;

    //存储服务相关的信息
    public static List<ZkData> serviceData=new ArrayList<>();

    public ZkClientImpl(String url){
        this.setURL(url);
        try {
            zk = new ZooKeeper(url,sessionTime, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return zk.getState()==ZooKeeper.States.CONNECTED;
    }

    public void setData(String path, byte[]data, int version, CreateMode createMode){
        try {
            if (zk.exists(path, false) == null) {
                zk.create(path,data, ZooDefs.Ids.OPEN_ACL_UNSAFE,createMode);
            }else{
                zk.setData(path,data,version);
            }
            zk.getChildren(path,true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ZkData getData(String path, int version, Boolean watcher){
        ZkData zkData=new ZkData();
        try {
            if(watcher==true)
                zkData=(ZkData) Tool.deserialize(zk.getData(path,new StateListenerImpl(),null),ZkData.class);
            else
                zkData=(ZkData) Tool.deserialize(zk.getData(path,false,null),ZkData.class);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return zkData;
        }

    }

    public static void addService(ZkData zkData){
        serviceData.add(zkData);
    }

    public static void changeService(ZkData zkData){
        for(int i=0;i<serviceData.size();i++){
            if(serviceData.get(i).getServiceId().equals(zkData.getServiceId())){
                serviceData.get(i).setIP(zkData.getIP());
                serviceData.get(i).setPort(zkData.getPort());
                serviceData.get(i).setServiceName(zkData.getServiceName());
            }
        }
    }

    public static void deleteService(ZkData zkData){
        serviceData.remove(zkData);
    }

    public void setURL(String url){
        this.url=url;
    }

    public void setSessionTime(int sessionTime){
        this.sessionTime=sessionTime;
    }
}
