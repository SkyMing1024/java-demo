package com.sky.io.nio.buffer;

import com.sky.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

@Slf4j
public class TestBuffer {

    public static void main(String[] args) {


        //
        byte[] bytes = {'a','b','c','d','1','2','3','4'};
        ByteBuffer heapBuffer = ByteBuffer.allocate(10);
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(12);

        ByteBuffer wrapBuffer1 = ByteBuffer.wrap(bytes);
        ByteBuffer wrapBuffer2 = ByteBuffer.wrap(bytes,2,4);
        ByteBufferUtil.debugAll(heapBuffer);
        ByteBufferUtil.debugAll(directBuffer);
        ByteBufferUtil.debugAll(wrapBuffer1);
        ByteBufferUtil.debugAll(wrapBuffer2);
        byte b = wrapBuffer1.get();
        System.out.println(b);
        System.out.println(wrapBuffer2.get());


        ByteBuffer sliceBuffer = wrapBuffer2.slice();
        ByteBufferUtil.debugAll(sliceBuffer);

        wrapBuffer2.put(3, (byte) 0x31);
        ByteBufferUtil.debugAll(wrapBuffer2);
        ByteBufferUtil.debugAll(sliceBuffer);

        directBuffer.order();
    }
    
}
