package com.sky.netty.ch01_hellonetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/*
Netty实现一个服务器
 */
public class HelloServer {
    public static void main(String[] args) {
        // 1.启动器，组装netty组件，启动服务器
        ServerBootstrap bootstrap = new ServerBootstrap()
                // 2.循环 监听事件
                .group(new NioEventLoopGroup()) // 可监听多种事件，accpet read等
                // 3.选择服务器的 ServerSocketChannel
                .channel(NioServerSocketChannel.class)
                // 4.添加处理器handler
                .childHandler(
                        // 5.通道初始化器，用来添加额外的handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new StringDecoder());// 内置Handler，将ByteBuf 装换为字符串
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() { // 自定义handler，Inbound为入站handler
                                    // 读事件
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        super.channelRead(ctx, msg);
                                        // 将上一步中的转换好的字符串输出
                                        System.out.println(msg);
                                    }
                                });
                            }
                        });
        // 7.绑定端口
        bootstrap.bind(8080);
    }
}
