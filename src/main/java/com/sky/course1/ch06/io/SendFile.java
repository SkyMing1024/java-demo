package com.sky.course1.ch06.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SendFile implements Runnable{
    private Socket socket;

    public SendFile(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("连接成功");
        OutputStream out = null;
        FileInputStream fileIn = null;
        try {
            // fileIn 用于将硬盘文件读入内存
            File file = new File("/Users/sky-mbp16/Pictures/2020-08-02 00.33.14.jpg");
            fileIn = new FileInputStream(file);
            // out用于将内存里的文件，远程发送到客户端
            out = socket.getOutputStream();
            byte[] bs = new byte[128];
            int len = -1;
            while ((len=fileIn.read(bs))!=-1){
                out.write(bs,0,len);
            }
            fileIn.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
}
