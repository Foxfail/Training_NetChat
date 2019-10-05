package com.foxfail.console_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatConsole {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private ChatConsole() {
        try {
            this.socket = new Socket("127.0.0.1", 27500);
            this.writer = new PrintWriter(socket.getOutputStream());
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            this.reader = new BufferedReader(streamReader);
            System.out.println("Network is ready");
            Thread readerThread = new Thread(new IncomingReader());
            readerThread.start();
            System.out.println("Ready to chat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatConsole chatConsole = new ChatConsole();
        //noinspection InfiniteLoopStatement
        while (true) {
            String message = chatConsole.getMessageFromConsole();
            chatConsole.sendMessage(message);
        }
//        String s = chatConsole.receiveMessage();
//        System.out.println(s);
    }

    private String getMessageFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите сообщение:");
        return scanner.nextLine();
    }

    private String receiveMessage() {
        try {
            Socket socket = new Socket("127.0.0.1", 27500);
            InputStreamReader stream = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(stream);
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendMessage(String message) {
        writer.println(message);
        writer.flush();
        System.out.println("Сooбщение отправлено");
    }


    public class IncomingReader implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
