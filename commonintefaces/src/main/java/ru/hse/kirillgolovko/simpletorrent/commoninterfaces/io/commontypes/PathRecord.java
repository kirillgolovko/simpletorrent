package ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes;

public class PathRecord {
    protected String path;

    public PathRecord(String path){
        this.path = path;
    }

    public PathRecord(){

    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }
}
