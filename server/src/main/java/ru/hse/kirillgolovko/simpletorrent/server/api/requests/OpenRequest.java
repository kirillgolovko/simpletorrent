package ru.hse.kirillgolovko.simpletorrent.server.api.requests;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileReader;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.ErrorResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.OpenResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.Response;

import java.io.IOException;

public class OpenRequest extends Request{
    private String path;


    public OpenRequest(String path, String clientId) {
        clientID = clientId;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public OpenRequest(){}

    @Override
    public Response process(IFilesystem filesystem) {
        IFileReader fileReader = filesystem.getFileReader();
        try{
            long blocks = fileReader.openFile(path);
            return new OpenResponse(blocks);
        } catch (IOException ex){
            return new ErrorResponse(ex.getMessage());
        }
    }
}
