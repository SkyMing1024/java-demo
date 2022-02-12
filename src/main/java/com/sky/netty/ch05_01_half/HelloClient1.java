package com.sky.netty.ch05_01_half;

import com.sun.xml.internal.stream.util.BufferAllocator;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class HelloClient1 {

    public static void main(String[] args) {


        Bootstrap bootstrap = new Bootstrap();
        try {
            Channel channel = bootstrap.group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ByteBuf buf = Unpooled.buffer();

                                    ctx.writeAndFlush(buf);
                                }
                            });

                        }
                    })
                    .connect(new InetSocketAddress("127.0.0.1", 8080))
                    .sync()
                    .channel();
            for (int i = 0; i < 10; i++) {
                channel.writeAndFlush("hello\n");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
