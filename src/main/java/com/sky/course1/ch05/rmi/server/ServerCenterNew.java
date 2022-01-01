package com.sky.course1.ch05.rmi.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerCenterNew implements ServerCenter{
    // map: 服务端所有可供客户端访问的接口，都注册到map中
    // key: 接口的名字
    // value：真正提供服务的类
    private static HashMap<String, Class> map = new HashMap<>();
    // 服务端口号
    private int port;
    // 线程池：连接池中存在多个线程对象，每个线程对象都可以处理一个客户请求
    private static ExecutorService executor
            = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    // 是否开启服务
    private static volatile boolean isRunning = false;

    public ServerCenterNew(int port) {
        this.port = port;
    }

    // 开启服务
    @Override
    public void start() {
        ServerSocket server = null;
        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        isRunning = true;
        while (true){
            System.out.println("服务已启动了哈哈哈哈...");
            Socket socket = null;
            try {
                socket = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            executor.execute(new ServiceTask(socket));
        }
    }

    // 关闭服务
    @Override
    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    // 将接口名和接口实现类一一对应，以便于接受到请求时，能及时获取到对应的服务实现类
    @Override
    public void register(Class service, Class serviceImpl) {
        map.put(service.getName(),serviceImpl);
    }

    private static class ServiceTask implements Runnable{
        private Socket socket;

        public ServiceTask() {
        }

        public ServiceTask(Socket socket) {
            this.socket = socket;
        }

        // 具体的处理逻辑
        @Override
        public void run() {
            ObjectOutputStream outputStream = null;
            ObjectInputStream inputStream = null;

            try {
                inputStream = new ObjectInputStream(socket.getInputStream());

                String serviceName = inputStream.readUTF();
                String methodName = inputStream.readUTF();
                Class[] parameterTypes = (Class[]) inputStream.readObject();
                Object[] arguments = (Object[]) inputStream.readObject();
                Class serviceClass = map.get(serviceName);
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(result);
                if (outputStream != null) outputStream.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
