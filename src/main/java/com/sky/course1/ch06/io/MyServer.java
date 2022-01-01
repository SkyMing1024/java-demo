package com.sky.course1.ch06.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) throws IOException {
        // 服务器地址：127.0.0.1:8888
        ServerSocket server = new ServerSocket(8888);
        // 允许接收多个客户端连接
        while (true){
            // 一直阻塞，直到有客户端发来连接
            Socket socket = server.accept();
            // 起一个线程处理客户端连接传输文件
//            new Thread(new SendFile(socket)).start();


            OutputStream out = null;
            FileInputStream fileIn = null;
            try {
                // fileIn 用于将硬盘文件读入内存
//                File file = new File("/Users/sky-mbp16/Pictures/2020-08-02 00.33.14.jpg");
                File file = new File("/Users/sky-mbp16/Movies/资料/极品身材白虎女友求我快放进去.mp4");
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
}
