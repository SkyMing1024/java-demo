package com.sky.netty.ch05_02_decode_encode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class TestLengthFrame {

    public static void main(String[] args) {

        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024,0,4,1,5),
                new LoggingHandler()
        );

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer,"Hello,world!");
        send(buffer,"Hi");
        channel.writeInbound(buffer);

    }

    private static void send(ByteBuf buffer,String content) {
        byte[] bytes = content.getBytes();
        int length = bytes.length;
        buffer.writeInt(length);
        buffer.writeByte(1);
        buffer.writeBytes(bytes);
    }
}
