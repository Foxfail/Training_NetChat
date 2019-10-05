package com.foxfail.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/** Класс релизующий Runnable
 * Для работы с клиентами в отдельном потоке
 * Конструктор принимает клиентский сокет,
 * через который идет прием сообшений клиента/отправка сообщений в клиент
 */
public class ClientHandler implements Runnable {

    Socket socket;
    BufferedReader reader;

    public ClientHandler(Socket clientSocket) {
        try {
            this.socket = clientSocket;
            InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        }catch (SocketException e){
            if (e.getMessage().equals("Connection reset")){
                System.out.println("Клиент отключился");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
