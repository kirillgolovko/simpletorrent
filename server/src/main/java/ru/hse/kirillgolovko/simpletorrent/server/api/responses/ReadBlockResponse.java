package ru.hse.kirillgolovko.simpletorrent.server.api.responses;

public class ReadBlockResponse extends Response{

    public ReadBlockResponse(byte[] data){
        rawData = data;
    }

    public ReadBlockResponse(){}
}
