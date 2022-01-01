package com.sky.io.nio.buffer;

import com.sky.util.ByteBufferUtil;

import java.nio.ByteBuffer;

/*
网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
    Hello,world\n
    I'm zhangsan\n
    How are you?\n
变成了下面的两个 byteBuffer (黏包，半包)
    Hello,world\nI'm zhangsan\nHo
    w are you?\n
现在要求你编写程序，将错乱的数据恢复成原始的按 \n 分隔的数据
*/
public class TestBufferExam {

    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(40);
        source.put("Hello,world!\nI`m sky!\nHow".getBytes());
        split(source);
        source.put(" are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source){
        // 开始读取缓冲区
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 找到一条完整的消息（以\n结尾）
            if (source.get(i) == '\n'){
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从source 读向target
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                System.out.println(target);
                ByteBufferUtil.debugAll(target);
            }
        }
        // 已经读取过的内容压缩，返回主函数继续向缓冲区填充内容
        source.compact();
    }

}
