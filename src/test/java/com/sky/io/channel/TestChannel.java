package com.sky.io.channel;


import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TestChannel {


    @Test
    public void testTransfer() throws IOException {
        FileChannel from = null;
        FileChannel to = null;
        try {
            from = new FileInputStream("test.txt").getChannel();
            to = new FileOutputStream("to.txt").getChannel();
            // 底层使用操作系统的零拷贝进行优化
            from.transferTo(0,from.size(),to);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            from.close();
            to.close();
        }

    }


    /**
     * transferTo底层利用零拷贝进行优化，最大只能传输2g数据
     */
    @Test
    public void testTransferBig(){

        try (   FileChannel from = new FileInputStream("test.txt").getChannel();
                FileChannel to = new FileOutputStream("to.txt").getChannel();
                ){
            // 要传输的文件大小
            long size = from.size();
            for (long left = size;left>0;){
                // 每次开始传输（size-left）
                // 每次计划传输大小 left
                long count = from.transferTo((size - left), left, to);
                System.out.println("positon:"+(size-left)+"   left:"+left);
                // 实际传输大小 count
                // left = 剩余传输大小
                left -= count;
            }

            from.close();
            to.close();
        }catch (IOException e){

        }finally {

        }





    }

}
