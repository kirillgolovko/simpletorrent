package ru.hse.kirillgolovko.simpletorrent.server.api.requests;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileReader;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.CloseResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.ErrorResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.Response;

import java.io.IOException;

public class CloseRequest extends Request {

    public CloseRequest(String clientId){
        clientID = clientId;
    }

    public CloseRequest(){}
    @Override
    public Response process(IFilesystem filesystem) {
        IFileReader fileReader = filesystem.getFileReader();
        try{
            fileReader.close();
            return new CloseResponse("Closed");
        } catch (IOException e){
            return new ErrorResponse(e.getMessage());
        }
    }
}
