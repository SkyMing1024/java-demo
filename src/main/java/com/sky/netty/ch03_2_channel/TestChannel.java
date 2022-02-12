package com.sky.netty.ch03_2_channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class TestChannel {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 1.连接到服务器
                // 异步非阻塞
                // main线程发起连接调用，真正执行connect的是nio线程
                .connect(new InetSocketAddress("localhost", 8080));
        // 方式一：使用sync同步处理结果
        // sync()的作用: 阻塞住当前线程，等nio线程 connect()方法异步执行完后再执行后面的方法
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        // 2. 向服务器发送数据
        channel.writeAndFlush("hello");


        /*
         方式二：
         使用addListener 方法异步处理结果
         */
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                // nio线程连接建立好后，会调用 operationComplete
                Channel channel1 = future.channel();
                log.debug("{}",channel1);
                channel1.writeAndFlush("from channel1");
            }
        });



    }

}
