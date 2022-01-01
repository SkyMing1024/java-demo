package com.sky.course1.ch05.rmi.server;

/**
 * 服务注册中心接口
 */
public interface ServerCenter {

    // 启动服务
    public void start();

    // 关闭服务
    public void stop();

    // 注册服务
    public void register(Class service, Class serviceImpl);


}
