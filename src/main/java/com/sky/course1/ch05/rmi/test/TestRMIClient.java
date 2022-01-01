package com.sky.course1.ch05.rmi.test;

import com.sky.course1.ch05.rmi.client.RMIClient;
import com.sky.course1.ch05.rmi.server.RMIService;

import java.net.InetSocketAddress;

public class TestRMIClient {
    public static void main(String[] args) throws ClassNotFoundException {
        RMIService service = RMIClient.getRemoteProxyObj(
                Class.forName("com.sky.course1.ch05.rmi.server.RMIService"),
                new InetSocketAddress("127.0.0.1",8080)
                );
        String s = service.sayHi("rmi sky");
        System.out.println(s);
    }
}
