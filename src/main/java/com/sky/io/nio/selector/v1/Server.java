package com.sky.io.nio.selector.v1;

import com.sky.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Server {

    /*
    单线程、阻塞模式下的服务器

     */
    public static void main(String[] args) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 1.创建服务器
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 2.绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(8080));

        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            // 3.accept，建立与客户端的连接
            log.debug("建立连接中...");
            SocketChannel socketChannel = serverSocketChannel.accept();
            log.debug("已建立连接 {}",socketChannel);
            channels.add(socketChannel);
            for (SocketChannel channel : channels){
                // 4.接收客户端的数据
                log.debug("开始写入数据 {}",channel);
                channel.read(buffer);
                buffer.flip();
                ByteBufferUtil.debugRead(buffer);
                buffer.clear();
                log.debug("写入数据结束 {}",channel);
            }
        }

    }

}
