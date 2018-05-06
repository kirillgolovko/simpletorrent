package ru.hse.kirillgolovko.simpletorrent.server.api.requests;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.IsUpResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.Response;

import java.time.LocalDateTime;

public class IsUpRequest extends Request {
    private String message;

    public IsUpRequest(String message, String clientId){
        this.message = message;
        this.clientID = clientId;
    }

    public IsUpRequest(){}

    public String getMessage() {
        return message;
    }

    @Override
    public Response process(IFilesystem filesystem) {
        return new IsUpResponse(LocalDateTime.now().toString());
    }
}
