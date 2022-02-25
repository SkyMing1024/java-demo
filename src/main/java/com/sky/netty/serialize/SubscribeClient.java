package com.sky.netty.serialize;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


public class SubscribeClient {

    public void conncet(int port, String host) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                // 禁止对类加载器进行缓存
                                new ObjectDecoder(1024,
                                        ClassResolvers.cacheDisabled(this.getClass().getClassLoader())
                                        )
                        );
                        ch.pipeline().addLast(new ObjectEncoder());
                        ch.pipeline().addLast(new SubReqClientHandler());

                    }
                });

        ChannelFuture f = b.connect(host, port).sync();
        f.channel().closeFuture().sync();

        group.shutdownGracefully();

    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8090;
        new SubscribeClient().conncet(port,"localhost");
    }
}
