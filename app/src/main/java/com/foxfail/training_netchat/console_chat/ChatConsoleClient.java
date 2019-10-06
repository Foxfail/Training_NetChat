package com.foxfail.training_netchat.console_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

class ChatConsoleClient {

    private PrintWriter writer;
    private BufferedReader reader;

    /**
     * Конструктор подготавливает сеть к двунаправленой связи с сервером
     * и запускает тред прослушивающий сообщения от сервера
     *
     * @param host ip-адрес хоста на котором запущен сервер
     * @param port порт на котором висит сервер
     */
    ChatConsoleClient(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            this.writer = new PrintWriter(socket.getOutputStream());
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            this.reader = new BufferedReader(streamReader);
            System.out.println("Network is ready");

            Thread readerThread = new Thread(new IncomingReader());
            readerThread.start();
            System.out.println("Ready to chat");
        } catch (ConnectException e) {
            if (e.getMessage().equals("Connection refused: connect")) {
                System.out.println("Server is not responding");
            } else {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отправляет сообщение на сервер
     *
     * @param message - сообщение которое необходимо отправить
     */
    void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    /**
     * Вложенный класс который реализует Runnable для запуска в отдельном потоке
     * Слушет входящие сообщения и выводит их на консоль
     */
    public class IncomingReader implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (SocketException e) {
                if (e.getMessage().equals("Connection reset")) {
                    System.out.println("Cannot connect to server");
                } else {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}