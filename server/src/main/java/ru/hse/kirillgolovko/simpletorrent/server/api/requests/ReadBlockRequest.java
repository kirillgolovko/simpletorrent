package ru.hse.kirillgolovko.simpletorrent.server.api.requests;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileReader;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.ErrorResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.ReadBlockResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.Response;

import java.io.IOException;

public class ReadBlockRequest extends Request{
    public ReadBlockRequest(String clientId){
        clientID = clientId;
    }

    public ReadBlockRequest(){}

    @Override
    public Response process(IFilesystem filesystem) {
        IFileReader fileReader = filesystem.getFileReader();
        try{
            return new ReadBlockResponse(fileReader.readNextBlock());
        } catch (IOException e){
            return new ErrorResponse(e.getMessage());
        }
    }
}
