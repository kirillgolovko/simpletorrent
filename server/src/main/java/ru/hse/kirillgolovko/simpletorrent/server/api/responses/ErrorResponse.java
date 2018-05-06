package ru.hse.kirillgolovko.simpletorrent.server.api.responses;

public class ErrorResponse extends Response{
     private String message;

     public ErrorResponse(String message){
         this.message = message;
     }

    public String getMessage() {
        return message;
    }

    public ErrorResponse(){}

    @Override
    public String toString() {
        return message;
    }
}
