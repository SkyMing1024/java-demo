package com.sky.netty.ch03_2_channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

@Slf4j
public class TestChannelClose {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        log.debug("{}",channel);
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (true){
                String nextLine = scanner.nextLine();
                if ("q".equals(nextLine)){
                    channel.close();
                    break;
                }else {
                    channel.writeAndFlush(nextLine);
                }
            }
        }).start();


        ChannelFuture closeFuture = channel.closeFuture();
//        System.out.println("等待关闭");
//        closeFuture.sync();
//        log.debug("处理关闭后的操作");

        closeFuture.addListener((ChannelFutureListener) future -> {
            log.debug("关闭后的操作");
            group.shutdownGracefully();
        });

    }

}
