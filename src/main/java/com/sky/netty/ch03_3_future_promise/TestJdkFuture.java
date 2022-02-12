package com.sky.netty.ch03_3_future_promise;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 创建线程池
        ExecutorService service = Executors.newFixedThreadPool(2);

        // 2. 提交任务
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1000);
                return 10;
            }
        });

        // 3. 主线程通过 future 获取结果
        // 阻塞住，直到拿到结果
        log.debug("等待结果");
        log.debug("结果：{}",future.get());
    }
}
