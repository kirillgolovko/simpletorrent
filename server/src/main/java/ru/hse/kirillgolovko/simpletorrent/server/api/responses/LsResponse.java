package ru.hse.kirillgolovko.simpletorrent.server.api.responses;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.PathRecord;

import java.util.List;

public class LsResponse extends Response{
    List<PathRecord> records;

    public LsResponse(List<PathRecord> records){
        this.records = records;
    }

    public List<PathRecord> getRecords() {
        return records;
    }

    public LsResponse(){}

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(PathRecord pathRecord : records){
            stringBuilder.append(pathRecord.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
