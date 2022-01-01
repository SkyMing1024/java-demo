package com.sky.netty.ch03;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;

public class TestEventLoop {
    public static void main(String[] args) {
        // 可以处理
        NioEventLoopGroup group = new NioEventLoopGroup();

        EventLoop next = group.next();

        System.out.println("核心数："+NettyRuntime.availableProcessors());
    }
}
