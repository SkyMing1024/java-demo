package com.sky.book_billion_traffic.ch07_netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class MyNettyServerHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        System.out.println("Server 收到消息："+ frame.text());

        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器收到的消息："+ frame.text() +"（这条信息来自服务器）"));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端加入，id = " + ctx.channel().id());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端移除，id = " + ctx.channel().id());

    }
}
