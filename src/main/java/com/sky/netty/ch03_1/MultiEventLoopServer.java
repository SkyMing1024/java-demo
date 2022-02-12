package com.sky.netty.ch03_1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class MultiEventLoopServer {
    public static void main(String[] args) {
        DefaultEventLoopGroup group = new DefaultEventLoopGroup();
        new ServerBootstrap()
                // 两个NioEventLoopGroup，boss和worker
                // boss负责ServerSocketChannel上的accept事件
                // worker负责 socketChannal上 read write事件
                .group(new NioEventLoopGroup(),new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("handler1",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                String res = buf.toString(Charset.defaultCharset());
                                log.debug("服务端收到："+res+",handler1处理");
                                // 消息传递给下一个Handler
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast(group,"my handler2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                String res = buf.toString(Charset.defaultCharset());
                                log.debug("服务端收到："+res+",handler2处理");
                            }
                        });
                    }
                })
        .bind(8080);
    }
}
