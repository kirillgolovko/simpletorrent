package ru.hse.kirillgolovko.simpletorrent.server.api.responses;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.DirectoryRecord;

public class PwdResponse extends Response {
    private DirectoryRecord path;

    public PwdResponse(DirectoryRecord path) {
        this.path = path;
    }

    public DirectoryRecord getPath() {
        return path;
    }

    public void setPath(DirectoryRecord path) {
        this.path = path;
    }

    PwdResponse(){}
}
