package ru.hse.kirillgolovko.simpletorrent.server.socketprocessor;

import ru.hse.kirillgolovko.simpletorrent.server.RootedFilesystem;
import ru.hse.kirillgolovko.simpletorrent.server.ServerMain;
import ru.hse.kirillgolovko.simpletorrent.server.api.Message;
import ru.hse.kirillgolovko.simpletorrent.server.api.requests.Request;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SocketProcessorThread implements Runnable {

    private Socket bindedSocket;

    public SocketProcessorThread(Socket socket) {
        this.bindedSocket = socket;
    }

    private String clientId;

    private LocalDateTime lastRequest;

    public void run() {
        try {
            IFilesystem filesystem = new RootedFilesystem(new File(ServerMain.params().getSharedDirPath()).getAbsolutePath());
            System.out.println("Got new client");
            lastRequest = LocalDateTime.now();
            while (!bindedSocket.isClosed() &&
                    ChronoUnit.SECONDS.between(lastRequest, LocalDateTime.now()) <= ServerMain.params().getClientKeepAlive()) {
                if (bindedSocket.getInputStream().available() > 0) {
                    Message message = Message.getMessageFromInputStream(bindedSocket.getInputStream());
                    lastRequest = LocalDateTime.now();
                    if (message.getMessageBody() instanceof Request) {
                        try {
                            Request request = (Request) message.getMessageBody();
                            clientId = request.getClientID();
                            System.out.println("Got " + request.getClass().getSimpleName() + " from Client : " + clientId);
                            Message.writeMessageToOutputStream(new Message(request.process(filesystem)),
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

            System.out.println("Client disconnected");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }
}
