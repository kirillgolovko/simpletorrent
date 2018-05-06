package ru.hse.kirillgolovko.simpletorrent.server.api.requests;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.ErrorResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.PwdResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.Response;

import java.io.IOException;

public class PwdRequest extends Request{
    public PwdRequest(String clientId){
        super.clientID = clientId;
    }

    public PwdRequest(){}

    @Override
    public Response process(IFilesystem filesystem) {
        try{
            return new PwdResponse(filesystem.pwd());
        } catch (IOException ex){
            return new ErrorResponse(ex.getMessage());
        }
    }
}
