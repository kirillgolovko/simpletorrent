package ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes;


import java.io.File;


public class DirectoryRecord extends PathRecord {
    protected String name;

    public DirectoryRecord(){

    }

    public DirectoryRecord(String path){
        this.setPath(path);
    }

    public String getName() {
        return name;
    }

    @Override
    public void setPath(String path) {
        super.setPath(path);
        name = new File(path).getName();
    }

    @Override
    public String toString() {
        return "Directory: " + name;
    }
}
