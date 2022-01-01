package com.sky.course1.ch05.rmi.test;

import com.sky.course1.ch05.rmi.server.*;

public class TestRMIServer {

    public static void main(String[] args) {
        // 用线程的形式启动服务
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 服务中心
                ServerCenter center = new ServerCenterNew(8080);
                // RMIService服务注册到服务中心
                center.register(RMIService.class, RMIServiceImpl.class);
                center.start();
            }
        }).start();
    }
}
