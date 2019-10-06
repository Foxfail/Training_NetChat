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

    /**
     * Процедура запуска сервера заключается в старте нового треда в котором
     * в цикле прослушивается канал на предмет новых подключений.
     */
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

    /**
     * serverSocket.accept() останавливает тред до тех пор пока не получит новое подключение
     * Как только новый клиент подключается, его соединение передается в новый поток ClientHandler
     * Также соединение вносится в список текущих соединений.
     */
    private void listenToMessage() {
        try {
            Socket clientSocket = serverSocket.accept();

            System.out.print("New client is connecting...");

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            clientOutputWriters.add(writer);

            Thread clientHadler = new Thread(new ClientHandler(clientSocket, this));
            clientHadler.start();

            System.out.println("ok");
            sendToAll("New client is connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Пробегает по списку клиентов и отправляет всем сообщение
     * @param message - сообщение которое необходимо разослать всем
     */
    synchronized void sendToAll(String message) {
        for (PrintWriter writer : clientOutputWriters) {
            writer.println(message);
            writer.flush();
        }
    }

}