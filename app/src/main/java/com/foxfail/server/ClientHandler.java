package com.foxfail.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/** Класс релизующий Runnable
 * Для работы с клиентами в отдельном потоке.
 * Конструктор принимает клиентский сокет,
 * через который идет прием сообшений клиента
 * и ссылка на инстанс сервера, осуществляющий взамимодействие с остальными клиентами
 */
public class ClientHandler implements Runnable {

    private BufferedReader reader;
    private ChatServer server;

    ClientHandler(Socket clientSocket, ChatServer chatServer) {
        try {
            this.server = chatServer;
            InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
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
                server.sendToAll(message);
            }
        }catch (SocketException e){
            if (e.getMessage().equals("Connection reset")){
                System.out.println("Client is offline");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}