package ru.hse.kirillgolovko.simpletorrent.api.requests;

import ru.hse.kirillgolovko.simpletorrent.api.Message;
import ru.hse.kirillgolovko.simpletorrent.api.MessageBody;
import ru.hse.kirillgolovko.simpletorrent.api.responses.IsUpResponse;
import ru.hse.kirillgolovko.simpletorrent.api.responses.Response;

import java.time.LocalDateTime;


public class Request extends MessageBody {
    public Response process(){
        System.out.println("Request" + jsonLenght + " " + rawDataLenght);
        return new IsUpResponse("Server is UP. Local time is " + LocalDateTime.now().toString());
    }
}
