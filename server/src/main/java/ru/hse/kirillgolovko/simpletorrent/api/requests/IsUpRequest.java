package ru.hse.kirillgolovko.simpletorrent.api.requests;

public class IsUpRequest extends Request {
    private String message;

    public IsUpRequest(String message){
        this.message = message;
    }

    public IsUpRequest(){}

    public String getMessage() {
        return message;
    }
}
