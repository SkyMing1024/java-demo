package com.sky.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyNettyServerTest {

    public static void main(String[] args) {
        NioEventLoopGroup woker = new NioEventLoopGroup();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            ChannelFuture future = bootstrap.group(boss, woker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
        /*
        IdleStateHandler：心跳机制处理器，主要用来检测远端是否读写超时，如果超时则将超时事件传入到userEventTriggered(ctx,evt)方法的evt参数中
        IdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds,int allIdleTimeSeconds,TimeUnit unit)的参数含义如下：
            readerIdleTimeSeconds：如果指定时间内没有检测到远端的读操作，则会触发形成IdleState.READER_IDLE(读空闲)状态；
            writerIdleTimeSeconds：如果指定时间内没有检测到远端的写操作，则会触发形成IdleState.WRITER_IDLE(写空闲)状态；
            allIdleTimeSeconds：如果指定时间内没有检测到远端的读和写操作，则会触发形成IdleState.ALL_IDLE(读写空闲)状态；
            unit:时间单位（默认：秒）
        */
                            sc.pipeline().addLast("IdleStateHandler",new IdleStateHandler(10,2,7, TimeUnit.SECONDS));

                            sc.pipeline().addLast(new ConnectStateHandler());

                            sc.pipeline().addLast("MyNettyServerHandler",new SimpleChannelInboundHandler(){
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, Object obj) throws Exception {

                                }

                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    if (evt instanceof IdleStateEvent){
                                        IdleStateEvent event = (IdleStateEvent) evt;
                                        // 获取超时事件：READER_IDLE、WRITER_IDLE或ALL_IDLE
                                        String eventType ="";

                                        switch (event.state()){
                                            case READER_IDLE:
                                                eventType = "读空闲";
                                                break;
                                            case WRITER_IDLE:
                                                eventType = "写空闲" ;
                                                break ;
                                            case ALL_IDLE:
                                                eventType = "读写空闲" ;
                                                break ;
                                        }
                                        System.out.println(ctx.channel().remoteAddress() + "超时事件：" + eventType);
                                        ctx.channel().close();
                                    }

                                }
                            });
                        }
                    }).bind(8080).sync();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            woker.shutdownGracefully();
            boss.shutdownGracefully();
        }


    }
}
