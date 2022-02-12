package com.sky.netty.ch04_1_ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

public class TestByteBuf {
    public static void main(String[] args) {

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        // 创建池化基于堆的ByteBuf
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.heapBuffer(10);
        // 创建池化基于直接内存的ByteBuf
        ByteBuf buf2 = ByteBufAllocator.DEFAULT.directBuffer(10);


        buf.writeBytes("123456 ".getBytes());

        log(buf);


        // test slice
        ByteBuf slice = buf.slice(0,3);

        log(slice);

        slice.setByte(1,'a');
        log(slice);
        log(buf);

        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        compositeByteBuf.addComponents(buf1,buf2);


    }

    private static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }
}
