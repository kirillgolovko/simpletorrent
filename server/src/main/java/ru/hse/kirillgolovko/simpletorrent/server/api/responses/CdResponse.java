package ru.hse.kirillgolovko.simpletorrent.server.api.responses;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.DirectoryRecord;

public class CdResponse extends Response{
    private DirectoryRecord directoryRecord;

    public CdResponse(DirectoryRecord directoryRecord) {
        this.directoryRecord = directoryRecord;
    }

    public DirectoryRecord getDirectoryRecord() {
        return directoryRecord;
    }

    public void setDirectoryRecord(DirectoryRecord directoryRecord) {
        this.directoryRecord = directoryRecord;
    }

    public CdResponse(){}
}
