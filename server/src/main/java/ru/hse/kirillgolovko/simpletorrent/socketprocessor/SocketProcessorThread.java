package ru.hse.kirillgolovko.simpletorrent.socketprocessor;

import ru.hse.kirillgolovko.simpletorrent.api.Message;
import ru.hse.kirillgolovko.simpletorrent.api.requests.Request;

import java.io.IOException;
import java.net.Socket;

public class SocketProcessorThread implements Runnable {

    private Socket bindedSocket;

    public SocketProcessorThread(Socket socket) {
        this.bindedSocket = socket;
    }

    public void run() {
        try {
            while (bindedSocket.isConnected() && !bindedSocket.isClosed()) {
                if (bindedSocket.getInputStream().available() > 0) {
                    Message message = Message.getMessageFromInputStream(bindedSocket.getInputStream());
                    if (message.getMessageBody() instanceof Request) {
                        try {
                            Message.writeMessageToOutputStream(new Message(((Request)message.getMessageBody()).process()),
                                    bindedSocket.getOutputStream());
                        } catch (Exception ex) {
                            System.err.println("Failed to process request");
                        }
                    } else {
                        System.err.println("Got not Request type message");
                    }
                } else {
                    Thread.yield();
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }
}
