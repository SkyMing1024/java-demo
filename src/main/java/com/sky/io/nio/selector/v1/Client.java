package com.sky.io.nio.selector.v1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));
        // 上述代码与下面代码等价
//        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));

        System.out.println("waiting......");
        System.out.println("发送数据");
        socketChannel.write(StandardCharsets.UTF_8.encode("hello美女"));
        System.out.println("发送数据结束");
    }

}
