package com.sky.io.nio.selector.v2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class WriteServer2 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        while (true){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iter = keys.iterator();

            while (iter.hasNext()){
                SelectionKey key = iter.next();
                iter.remove();

                if (key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey sckey = sc.register(selector, 0, null);
                    sckey.interestOps(SelectionKey.OP_READ);

                    // 向客户端发送大量数据
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 5000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());

                    int cnt = 1;
                    // 返回实际写入的字节数
                    int write = sc.write(buffer);
                    System.out.println((cnt++) +"次写入字节数："+write);

                    // 判断是否有剩余内容
                    if (buffer.hasRemaining()){
                        // 增加关注可写事件
                        sckey.interestOps(sckey.interestOps()|SelectionKey.OP_WRITE);
                        // 把未写完的数据挂到selectionKey上
                        sckey.attach(buffer);
                    }
                }else if (key.isWritable()){
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(buffer);
                    System.out.println("WRITE写入："+write);
                    // 6. 清理操作
                    if (!buffer.hasRemaining()) {
                        key.attach(null); // 需要清除buffer
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);//不需关注可写事件
                    }
                }

            }

        }


    }
}
