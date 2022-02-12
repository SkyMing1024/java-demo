package com.sky.netty.echo_server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1){
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
        }
        // 设置端口值
        int port = Integer.parseInt(args[0]);

        // 调用服务器start方法
        new EchoServer(port).start();
    }

    private void start() throws InterruptedException  {
            EchoServerHandler handler = new EchoServerHandler();
            // 创建EventLoopGroup
            NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            // 创建ServerBootStrap
            ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(eventLoopGroup)
//                指定所使用的的Nio传输Channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    // 添加一个EchoServerHandler到channel的CHannelPipLine
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline().addLast(handler);
                        }
                    });
            // 异步地绑定服务器； 调用 sync()方法阻塞 等待直到绑定完成
            // 你绑定了服务器 ，并等待绑定完成。对 sync() 方法的调用将导致当前 Thread 阻塞，一直到绑定操作完成为止）。
            ChannelFuture future = bootstrap.bind().sync();
            // 获取 Channel 的 CloseFuture， 并 且阻塞当前线 程直到它完成
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // 关闭EventLoopGrou，释放所有资源
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
