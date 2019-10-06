package com.foxfail.training_netchat.console_chat;

import java.util.Scanner;

public class MainChatConsoleClient {
    public static void main(String[] args) {
        String encoding = System.getProperty("console.encoding", "utf-8");
        System.out.println("encoding:" + encoding);
        Scanner scanner = new Scanner(System.in, encoding);
        System.out.print("Enter server ip address:");
        String serverAdress = scanner.nextLine();

        Integer port = null;
        while (port == null) {
            System.out.print("Enter server port:");
            try {
                port = Integer.valueOf(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Server port need to be a number 1-65535");
            }
        }


        ChatConsoleClient chatConsoleClient = new ChatConsoleClient(serverAdress, port);
        //noinspection InfiniteLoopStatement
        while (true) {
            String message = scanner.nextLine();
            chatConsoleClient.sendMessage(message);
        }
    }
}