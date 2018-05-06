package ru.hse.kirillgolovko.simpletorrent.server.api.responses;

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
