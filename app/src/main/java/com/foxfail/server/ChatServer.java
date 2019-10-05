package com.foxfail.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private ServerSocket serverSocket;
    private List<PrintWriter> clientOutputWriters;

    private ChatServer() {
        try {
            System.out.println("Server starting...");
            clientOutputWriters = new ArrayList<>();
            serverSocket = new ServerSocket(27500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        System.out.println("Server start to listen");
        //noinspection InfiniteLoopStatement
        while (true) {
            chatServer.listenToMessage();
        }
//            PrintWriter writer = new PrintWriter(socket.getOutputStream());
//            String message = "test";
//            writer.println(message);
//            writer.close();
//            reader.close();
//            System.out.println("Исходящее сообщение:" + message);
    }

    private void listenToMessage() {
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.print("Новый клиент подключается...");

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            clientOutputWriters.add(writer);

            Thread clientHadler = new Thread(new ClientHandler(clientSocket));
            clientHadler.start();

            System.out.println("Подключен");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
