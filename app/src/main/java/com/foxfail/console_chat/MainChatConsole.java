package com.foxfail.console_chat;

import java.util.Scanner;

public class MainChatConsole {
    public static void main(String[] args) {
        ChatConsole chatConsole = new ChatConsole("127.0.0.1", 27500);
        //noinspection InfiniteLoopStatement
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            chatConsole.sendMessage(message);
        }
    }
}
