package com.sky.course1.ch06.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NIOSendFile {
    public static void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        FileChannel outFileChannel = FileChannel.open(Paths.get("/Users/sky-mbp16/CODING/FILE_UP_LOAD/abc copy.txt"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        // 服务绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8888));
        // 创建与客户端建立连接的SocketChannel对象
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("连接成功");

        long start = System.currentTimeMillis();

        // 分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 接收客户端发送的文件，并保存到本地
        while (socketChannel.read(buffer)!=-1){
            buffer.flip();
            outFileChannel.write(buffer);
            buffer.clear();
        }
        System.out.println("接收成功");
        socketChannel.close();
        outFileChannel.close();
        serverSocketChannel.close();
        long end = System.currentTimeMillis();
        System.out.println("服务端接收文件耗时："+(end-start));
    }

    /**
     * 此例使用的是非直接缓冲区
     * @throws IOException
     */
    public static void client() throws IOException {
        FileChannel inFileChannel = FileChannel.open(Paths.get("/Users/sky-mbp16/CODING/FILE_UP_LOAD/abc.txt"),StandardOpenOption.READ);
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        // 分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long start = System.currentTimeMillis();
        //读取本地文件，并发送到服务端
        while (inFileChannel.read(buffer) != -1) {
            buffer.rewind();
            socketChannel.write(buffer);
            buffer.clear();
        }
        if (inFileChannel != null) inFileChannel.close();
        if (socketChannel != null) socketChannel.close();
        long end = System.currentTimeMillis();
        System.out.println("使用非直接缓冲区发送文件耗时：" + (end - start));
    }


    /**
     *
     * @throws IOException
     */
    public static void client2() throws IOException {
        long start = System.currentTimeMillis();
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        FileChannel inFileChannel = FileChannel.open(Paths.get("/Users/sky-mbp16/CODING/FILE_UP_LOAD/abc.txt"),StandardOpenOption.READ);
        // 通过size()获取文件大小，从而在内核地址空间中开辟与大小文件相同的直接缓冲区
        inFileChannel.transferTo(0,inFileChannel.size(),socketChannel);
        inFileChannel.close();
        socketChannel.close();
        long end = System.currentTimeMillis();
        System.out.println("使用直接缓冲区客户端发送文件耗时：" + (end - start));
    }

    public static void main(String[] args) throws IOException {
        server();
    }
}
