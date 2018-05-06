package ru.hse.kirillgolovko.simpletorrent.client;

import ru.hse.kirillgolovko.simpletorrent.server.api.Message;
import ru.hse.kirillgolovko.simpletorrent.server.api.requests.IsUpRequest;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.IsUpResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.Response;

import java.net.Socket;
import java.time.LocalDateTime;

public class ServerPinger implements Runnable {

    private final ClientApp clientApp;

    private final Socket socket;

    private final String clientId;

    public ServerPinger(ClientApp app, Socket socket, String clientId){
        this.clientApp = app;
        this.socket = socket;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Response response;
                synchronized (socket){
                    Message.writeMessageToOutputStream(new Message(new IsUpRequest("Hi", clientId)), socket.getOutputStream());
                    response = (Response) Message.getMessageFromInputStream(socket.getInputStream()).getMessageBody();
                }
                if(response instanceof IsUpResponse){
                    clientApp.setLastServerActivity(LocalDateTime.now());
                } else {
                    throw new Exception("ex");
                }
                Thread.sleep(200);
            }
        } catch (Exception ex){
            clientApp.setServerDiconnected();
            return;
        }
    }
}
