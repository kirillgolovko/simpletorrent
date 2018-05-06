package ru.hse.kirillgolovko.simpletorrent.server.api.requests;

import ru.hse.kirillgolovko.simpletorrent.server.api.responses.ErrorResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.LsResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.Response;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;

import java.io.IOException;

public class LsRequest extends Request {

    public LsRequest(String clientID){
        super.clientID = clientID;
    }

    LsRequest(){

    }

    @Override
    public Response process(IFilesystem filesystem) {
        try {
            return new LsResponse(filesystem.ls());
        } catch (IOException ex){
            return new ErrorResponse(ex.getMessage());
        }
    }
}
