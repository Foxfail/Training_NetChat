package com.foxfail.console_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ChatConsole {

    private PrintWriter writer;
    private BufferedReader reader;

    ChatConsole(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
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

    void sendMessage(String message) {
        writer.println(message);
        writer.flush();
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
