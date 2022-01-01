package com.sky.io.nio.channel;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class TestChannel {

    public static void main(String[] args) throws FileNotFoundException {
        RandomAccessFile file = new RandomAccessFile("","rw");
        FileChannel channel = file.getChannel();


    }

}
