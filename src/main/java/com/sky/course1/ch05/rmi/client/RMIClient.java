package com.sky.course1.ch05.rmi.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RMIClient {

    public static <T> T getRemoteProxyObj(Class serviceInterface, InetSocketAddress addr){
        Object newProxyInstance = Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                new Class<?>[]{serviceInterface},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 客户端向服务端发送请求：请求某一个具体的接口
                        Socket socket = new Socket();
                        ObjectOutputStream output = null;
                        ObjectInputStream input = null;
                        // addr包含了要访问的服务端的IP和端口
                        socket.connect(addr);
                        // 通过序列化流（对象流）想服务端发送请求
                        output = new ObjectOutputStream(socket.getOutputStream());
                        // 发送请求接口名
                        output.writeUTF(serviceInterface.getName());
                        // 发送请求方法名
                        output.writeUTF(method.getName());
                        // 发送请求方法的参数的类型
                        output.writeObject(method.getParameterTypes());
                        // 发送请求方法的参数值
                        output.writeObject(args);

                        // 等待服务端处理
                        // ......

                        // 接收服务端处理后的返回值
                        input = new ObjectInputStream(socket.getInputStream());

                        Object returnObject = input.readObject();

                        if (output != null) output.close();
                        if (input != null) input.close();

                        return returnObject;

                    }
                });

        return (T) newProxyInstance;
    }

}
