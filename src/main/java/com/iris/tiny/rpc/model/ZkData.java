package com.iris.tiny.rpc.model;

import java.io.Serializable;

public class ZkData implements Serializable{

    static private final long serialVersionUID = -4364536436151723421L;

    String IP;

    String Port;

    String ServiceId;

    String ServiceName;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    @Override
    public String toString() {
        return "ZkData{" +
                "IP='" + IP + '\'' +
                ", Port='" + Port + '\'' +
                ", ServiceId='" + ServiceId + '\'' +
                ", ServiceName='" + ServiceName + '\'' +
                '}';
    }
}
