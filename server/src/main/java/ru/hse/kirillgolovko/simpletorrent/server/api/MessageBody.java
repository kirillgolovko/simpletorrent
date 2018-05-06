package ru.hse.kirillgolovko.simpletorrent.server.api;

public class MessageBody {
    protected byte[] rawData;
    protected int jsonLenght;
    protected int rawDataLenght;

    public byte[] getRawData() {
        return rawData;
    }

    public void setRawData(byte[] rawData) {
        this.rawData = rawData;

    }
}
