package com.sky.io.nio.buffer;

import com.sky.util.ByteBufferUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestBufferGather {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("words.txt", "rw");
        FileChannel channel = accessFile.getChannel();
        ByteBuffer b1 = ByteBuffer.allocate(3);
        ByteBuffer b2 = ByteBuffer.allocate(4);
        ByteBuffer b3 = ByteBuffer.allocate(5);
        ByteBuffer[] buffers = new ByteBuffer[]{b1,b2,b3};
        channel.read(buffers);
        ByteBufferUtil.debugAll(b1);
        ByteBufferUtil.debugAll(b2);
        ByteBufferUtil.debugAll(b3);
    }
}
