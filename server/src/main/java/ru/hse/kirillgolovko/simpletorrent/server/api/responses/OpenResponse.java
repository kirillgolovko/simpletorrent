package ru.hse.kirillgolovko.simpletorrent.server.api.responses;

public class OpenResponse extends Response{
    private long totalBlocks;

    public OpenResponse(long totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    public long getTotalBlocks() {
        return totalBlocks;
    }

    public void setTotalBlocks(long totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    public OpenResponse(){}
}
