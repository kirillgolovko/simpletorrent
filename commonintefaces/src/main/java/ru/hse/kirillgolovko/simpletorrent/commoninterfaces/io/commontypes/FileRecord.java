package ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class FileRecord extends PathRecord{

    private final static ZoneOffset zoneOffset;

    static {
        zoneOffset = ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now());
    }

    private String name;

    private String lastModified;

    private long size;

    public FileRecord(){

    }

    public FileRecord(String path){
        setPath(path);
        File file = new File(path);
        lastModified = new Date(file.lastModified()).toString();
        size = file.length();
    }

    public FileRecord(String path, String lastModified, long size){
        setPath(path);
        this.lastModified = lastModified;
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModified() {
        return lastModified;
    }

    @Override
    public void setPath(String path) {
        super.setPath(path);
        name = new File(path).getName();
    }

    @Override
    public String toString() {
        return "File: " +
                name +
                "\t Last Modified: " +
                lastModified +
                "\t Size: " +
                size;
    }
}
