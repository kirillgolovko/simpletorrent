package ru.hse.kirillgolovko.simpletorrent.server.api.requests;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.DirectoryRecord;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.CdResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.ErrorResponse;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.Response;

import java.io.IOException;

public class CdRequest extends Request{
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public CdRequest(String path, String clientId){
        super.clientID = clientId;
        this.path = path;
    }

    public CdRequest(){}

    @Override
    public Response process(IFilesystem filesystem) {
        try {
            DirectoryRecord directoryRecord = filesystem.cd(path);
            return new CdResponse(directoryRecord);
        } catch (IOException ex){
            return new ErrorResponse(ex.getMessage());
        }
    }
}
