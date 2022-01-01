package com.sky.course1.ch06.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        Socket socket = new Socket("127.0.0.1", 8888);
        // in，用于接受服务端远程发来的文件，并将文件保存在内存中
        InputStream in = socket.getInputStream();
        byte[] bs = new byte[128];
        int len = -1;
        File newFile = new File("/Users/sky-mbp16/CODING/FILE_UP_LOAD/test.mp4");
        if (!newFile.exists()){
            if (!newFile.getParentFile().exists() ){
                newFile.getParentFile().mkdirs();
            }
        }
        // 将内存中的文件保存在客户端本地硬盘
        FileOutputStream fileOut = new FileOutputStream(newFile);
        while ((len = in.read(bs)) != -1){
            fileOut.write(bs,0,len);
        }
        System.out.println("文件接受成功");
        fileOut.close();
        in.close();
        socket.close();
        long end = System.currentTimeMillis();
        System.out.println("用时："+ (end-start));
    }
}
