package com.foxfail.console_chat;

import java.util.Scanner;

public class MainChatConsoleClient {
    public static void main(String[] args) {
        ChatConsoleClient chatConsoleClient = new ChatConsoleClient("127.0.0.1", 27500);
        //noinspection InfiniteLoopStatement
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            chatConsoleClient.sendMessage(message);
        }
    }
}