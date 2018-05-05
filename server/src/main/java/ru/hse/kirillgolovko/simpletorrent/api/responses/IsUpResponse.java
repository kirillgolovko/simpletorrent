package ru.hse.kirillgolovko.simpletorrent.api.responses;

import ru.hse.kirillgolovko.simpletorrent.api.requests.IsUpRequest;

public class IsUpResponse extends Response{
    private String message;

    public IsUpResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public IsUpResponse(){
    }
}
