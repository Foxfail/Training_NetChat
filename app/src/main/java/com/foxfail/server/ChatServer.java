package com.foxfail.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ChatServer {

    private ServerSocket serverSocket;
    private List<PrintWriter> clientOutputWriters;

    ChatServer(int port) {
        try {
            clientOutputWriters = new ArrayList<>();
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void start() {
        System.out.print("Server starting...");

        new Thread(new Runnable() {
            @SuppressWarnings("InfiniteLoopStatement")
            @Override
            public void run() {
                while (true) {
                    listenToMessage();
                }
            }
        }).start();

        System.out.println("ok");
    }

    private void listenToMessage() {
        try {
            Socket clientSocket = serverSocket.accept();

            System.out.print("New client is connecting...");

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            clientOutputWriters.add(writer);

            Thread clientHadler = new Thread(new ClientHandler(clientSocket, this));
            clientHadler.start();

            System.out.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized void sendToAll(String message) {
        for (PrintWriter writer : clientOutputWriters) {
            writer.println(message);
            writer.flush();
        }
    }

}
