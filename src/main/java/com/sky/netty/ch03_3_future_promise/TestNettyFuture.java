package com.sky.netty.ch03_3_future_promise;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        EventLoop loop = group.next();

        Future<Object> future = loop.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1000);
                return 10;
            }
        });

        // 同步方式获取结果, 主线程获取到结果
        log.debug("等待结果");
//        log.debug("结果：{}",future.get());

        // 异步方式获取结果，nio线程获取到结果
        future.addListener(new GenericFutureListener<Future<? super Object>>() {
            @Override
            public void operationComplete(Future<? super Object> future) throws Exception {
                log.debug("接收结果: {}",future.get());
            }
        });


    }
}
