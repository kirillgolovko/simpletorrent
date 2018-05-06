package ru.hse.kirillgolovko.simpletorrent.server.api.responses;

import ru.hse.kirillgolovko.simpletorrent.server.api.requests.Request;

public class CloseResponse extends Response{
    private String message;

    public CloseResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private CloseResponse(){}
}
