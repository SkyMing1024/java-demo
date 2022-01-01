package com.sky;

import java.io.IOException;

public class ServerSocket {

    public static void main(String[] args) throws IOException {
        java.net.ServerSocket server = new java.net.ServerSocket(8889,6);
        while (true){
            server.accept();
        }
    }


}
