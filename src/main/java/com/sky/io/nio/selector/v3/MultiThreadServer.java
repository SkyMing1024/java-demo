package com.sky.io.nio.selector.v3;

import com.sky.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, SelectionKey.OP_ACCEPT, null);
        ssc.bind(new InetSocketAddress(8080));
        // 创建固定数量的worker,并调用激活worker
        Worker worker = new Worker("worker-0");

        while (true){
            boss.select();
            Set<SelectionKey> bossKeys = boss.selectedKeys();
            Iterator<SelectionKey> iterator = bossKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);

                    log.debug("conneted...{}",sc.getRemoteAddress());
                    // 将channel注册到worker，监听读事件
                    log.debug("before register...{}",sc.getRemoteAddress());
                    worker.register(sc);
                    sc.register(worker.selector,SelectionKey.OP_READ,null);
                    log.debug("after register...{}",sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        private boolean flag = false;

        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        /**
         * 初始化线程和selector
         */
        public void register(SocketChannel sc) throws IOException {
            if (!flag){
                thread = new Thread(this,name);
                thread.start();
                selector = Selector.open();
                flag = true;
            }
            // 此方法仍然在主线程（boss线程）执行
            queue.add(()->{
                try {
                    sc.register(selector,SelectionKey.OP_READ,null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            // 唤醒select方法
            selector.wakeup();

        }

        @Override
        public void run() {
            while (true){
                try {
                    selector.select();

                    Runnable task = queue.poll();
                    if (task!=null){
                        task.run();
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            log.debug("read...{}",channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
