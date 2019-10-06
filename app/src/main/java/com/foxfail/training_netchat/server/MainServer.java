package com.foxfail.training_netchat.server;

import java.util.Scanner;

public class MainServer {
    public static void main(String[] args) {
        System.out.print("Enter server port to start on: ");
        Scanner scanner = new Scanner(System.in);
        Integer port = Integer.valueOf(scanner.nextLine());
        ChatServer chatServer = new ChatServer(port);
        chatServer.start();
    }
}