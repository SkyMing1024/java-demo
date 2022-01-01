package com.sky.course1.ch05.rmi.server;

public class RMIServiceImpl implements RMIService{
    @Override
    public String sayHi(String name) {
        return "Hi，" + name;
    }
}
