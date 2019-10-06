package com.foxfail.server;

public class MainServer {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(27500);

        chatServer.start();
    }
}
