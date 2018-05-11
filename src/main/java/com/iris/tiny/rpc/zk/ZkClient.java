package com.iris.tiny.rpc.zk;

import com.iris.tiny.rpc.model.ZkData;
import org.apache.zookeeper.*;

import java.io.IOException;

public interface ZkClient {
    public boolean isConnected();

    public void setData(String path, byte[]data, int version, CreateMode createMode);

    public ZkData getData(String path, int version, Boolean watcher);
}