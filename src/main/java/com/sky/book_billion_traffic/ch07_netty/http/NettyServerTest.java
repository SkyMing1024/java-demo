package com.sky.book_billion_traffic.ch07_netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class NettyServerTest {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new HttpServerCodec());
                        sc.pipeline().addLast(new SimpleChannelInboundHandler<HttpObject>() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                String str = (String) msg;
                                System.out.println(str);
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
                                if (msg instanceof HttpRequest) {
                                    HttpRequest request = (HttpRequest) msg;
                                    URI uri = new URI(request.uri());

                                    if (!"/favicon.ico".equals(uri.getPath())) {
                                        System.out.println("channelRead0 invoke...");
                                        //ByteBuf对象：定义响应的内容
                                        ByteBuf content = Unpooled.copiedBuffer("sdjfjdslfjdslkj", CharsetUtil.UTF_8);
                                        //FullHttpResponse对象：响应对象，定义响应的具体信息
                                        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                                        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                                        //content.readableBytes() ：响应内容的长度
                                        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                                        //将响应 返回给客户端
                                        ctx.writeAndFlush(response);
                                    }
                                }
                            }

                            //当增加新的处理器时，触发此方法
                            @Override
                            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("1.handlerAdded(),增加了新的处理器...");
                                super.handlerAdded(ctx);
                            }

                            //当通道被注册到一个事件循环组EventLoop上时，执行此方法
                            @Override
                            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("2.channelRegistered(),通道被注册...");
                                super.channelRegistered(ctx);
                            }

                            //当通道处于活跃状态（连接到某个远端，可以收发数据）时，执行此方法
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("3.channelActive(),通道连接到了远端，处于活跃状态...");
                                super.channelActive(ctx);
                            }

                            //当通道处于非活跃状态（与远端断开了连接）时，执行此方法
                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("4.channelInactive(),通道远端断开了连接，处于非活跃状态... ");
                                super.channelInactive(ctx);
                            }

                            //当通道被取消注册时，执行此方法
                            @Override
                            public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("5.channelUnregistered(),通道被取消了注册...");
                                super.channelUnregistered(ctx);
                            }

                            //当程序发生异常时，执行此方法
                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                cause.printStackTrace();
                                ctx.close();
                            }
                        });
                    }
                });


        try {
            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }
}
