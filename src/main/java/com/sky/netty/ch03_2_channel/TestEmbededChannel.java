package com.sky.netty.ch03_2_channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestEmbededChannel {


    @Test
    public void testFixedFrameDecoder(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertTrue(channel.writeInbound(input.retain()));
        assertTrue(channel.finish());

        // read messages
        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();


    }

    class FixedLengthFrameDecoder extends ByteToMessageDecoder {
        private final int frameLength;

        public FixedLengthFrameDecoder(int frameLength) {
            if (frameLength <= 0) {
                throw new IllegalArgumentException( "frameLength must be a positive integer: " + frameLength);
            }
            this.frameLength = frameLength;
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            // 检查是否有足够的字节可被读取
            while (in.readableBytes() >= frameLength ){
                // 从ByteBuf中读取下一帧
                ByteBuf buf = in.readBytes(frameLength);
                // 将该帧添加到已被解码的消息列表中
                out.add(buf);
            }
        }
    }

}
