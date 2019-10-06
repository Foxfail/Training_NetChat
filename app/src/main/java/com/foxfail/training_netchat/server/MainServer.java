package com.foxfail.training_netchat.server;

import java.util.Scanner;

public class MainServer {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(27500);

        chatServer.start();
    }
}