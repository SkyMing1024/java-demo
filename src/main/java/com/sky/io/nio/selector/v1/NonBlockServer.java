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
public class NonBlockServer {
    /*
    非阻塞模式下，使用服务器。
    各个线程相互不干扰，但存在cpu空转问题
     */
    public static void main(String[] args) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 1.创建服务器
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        // 2.绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(8080));

        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            // 3.accept，建立与客户端的连接
            // 如果以非阻塞模式被调用，当没有传入连接在等待时，ServerSocketChannel.accept( )会立即返 回 null
//            log.debug("建立连接中...");
            SocketChannel socketChannel = serverSocketChannel.accept();

            if (socketChannel!=null){
                log.debug("已建立连接 {}",socketChannel);
                // 非阻塞模式
                socketChannel.configureBlocking(false);
                channels.add(socketChannel);
            }
            for (SocketChannel channel : channels){
                // 4.接收客户端的数据
//                log.debug("开始写入数据 {}",channel);
                int read = channel.read(buffer);
                if (read>0){
                    buffer.flip();
                    ByteBufferUtil.debugRead(buffer);
                    buffer.clear();
                    log.debug("写入数据结束 {}",channel);
                }
            }
        }

    }

}
