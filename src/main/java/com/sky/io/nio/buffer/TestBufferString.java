package com.sky.io.nio.buffer;

import com.sky.util.ByteBufferUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * ByteBuffer 与 String 的转换
 */
public class TestBufferString {
    public static void main(String[] args) {
        // 1.字符串getBytes得到字符数组
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ByteBufferUtil.debugAll(buffer);
        byte[] bytes = "hello".getBytes();
        buffer.put(bytes);
        ByteBufferUtil.debugAll(buffer);


        // 利用charset指定编码转换为String,并且直接切换为读模式
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtil.debugAll(buffer1);

        // 同样利用charset可以从Buffer中读出字符串，但是需要注意，decode(Buffer)方法中的参数buffer必须为读模式
        String s = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(s);


    }


}
