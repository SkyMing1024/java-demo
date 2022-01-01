package com.sky.io.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TestChannelTransfer1 {
    public static void main(String[] args) throws IOException {
        FileChannel from = new FileInputStream("test.txt").getChannel();
        FileChannel to = new FileOutputStream("to.txt").getChannel();
        // 底层使用操作系统的零拷贝进行优化
        from.transferTo(0,from.size(),to);
        from.close();
        to.close();
    }
}
