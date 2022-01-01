package com.sky;

import java.net.Socket;

public class ClientSocket {

    private static Socket[] clients = new Socket[30];

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 4; i++) {
            clients[i] = new Socket("127.0.0.1", 8889);
            System.out.println("Client:" + i);
        }
    }
}
