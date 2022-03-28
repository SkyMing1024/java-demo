package com.sky.book_billion_traffic.ch07_netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class MyNettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new HttpServerCodec());
        ch.pipeline().addLast(new HttpObjectAggregator(4096));
        //处理websocket的netty处理器，可以通过构造方法绑定webSocket的服务端地址
        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/myWebSocket"));

        ch.pipeline().addLast("MyNettyServerHandler", new MyNettyServerHandler());


    }
}
