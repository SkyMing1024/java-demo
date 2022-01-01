package com.sky.io.transfile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9999);
        while (true){
            Socket socket = server.accept();
            new Thread(new SendFile(socket)).start();
        }
    }
}
