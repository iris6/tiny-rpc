package com.iris.tiny.rpc.demo.service;

public class RaceException extends RuntimeException{
    private String flag = "race";
    public RaceException(String message){
        super(message);
    }
    public RaceException(Exception e){
        super(e);
    }
    public String getFlag(){
        return flag;
    }
}
