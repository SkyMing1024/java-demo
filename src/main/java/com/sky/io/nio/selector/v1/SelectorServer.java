package com.sky.io.nio.selector.v1;

import com.sky.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class SelectorServer {
    public static void main(String[] args) throws IOException {
        // 1. 创建Selector
        Selector selector = Selector.open();

        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        log.debug("ServerSocketChannel 1 {}",serverSocketChannel);

        // 2. channel注册到selector上
        // 通过SelectionKey可以知道是什么事件、哪个通道的事件
        SelectionKey selectionKey = serverSocketChannel.register(selector, 0, null);
        // 此key监听的事件
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key {}",selectionKey);

        serverSocketChannel.bind(new InetSocketAddress(8080));

        while (true){
            // 3.select 没有事件发生，线程阻塞；有时间发生，线程继续执行
            // 事件未处理(也未取消)时，select不会阻塞
            selector.select();

            // 4.处理事件  selectionKeys内部包含所有发生的事件
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                log.debug(" key {}",key);
                // 区分事件类型,为不同类型的时间定义不同的处理逻辑
                if (key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);

                    SelectionKey socketKey = socketChannel.register(selector, 0, null);
                    socketKey.interestOps(SelectionKey.OP_READ);
                    log.debug("处理accept事件结束 {}",socketChannel);
                }else if (key.isReadable()){
                    SocketChannel channel = ((SocketChannel) key.channel());
                    ByteBuffer byteBuffer = ByteBuffer.allocate(4);
                    int read = channel.read(byteBuffer);
                    // read == -1,代表是客户端正常断开连接
                    if (read == -1){
                        key.cancel();
                    }else {
                        byteBuffer.flip();
                        ByteBufferUtil.debugRead(byteBuffer);
                    }
                }

                // 一个事件处理完，一定一定要手动对应的key移除，否则会继续执行该事件，且报空指针异常
                iterator.remove();
            }
        }

    }
}
