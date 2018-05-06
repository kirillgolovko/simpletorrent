package ru.hse.kirillgolovko.simpletorrent.server.api.requests;

import ru.hse.kirillgolovko.simpletorrent.server.api.MessageBody;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.IsUpResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.Response;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;

import java.time.LocalDateTime;


public class Request extends MessageBody {

    protected String clientID;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public Response process(IFilesystem filesystem){
        System.out.println("Request " + jsonLenght + " " + rawDataLenght);
        return new IsUpResponse("Server is UP. Local time is " + LocalDateTime.now().toString());
    }
}
