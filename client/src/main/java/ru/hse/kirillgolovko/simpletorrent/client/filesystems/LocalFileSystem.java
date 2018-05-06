package ru.hse.kirillgolovko.simpletorrent.client.filesystems;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.DirectoryRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.FileRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.PathRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileReader;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileWriter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class LocalFileSystem implements IFilesystem{

    private LocalFileWriter fileWriter;

    private File workingDir;

    public LocalFileSystem(File workingDir){
        this.workingDir = workingDir;
        fileWriter = new LocalFileWriter();
    }

    public List<PathRecord> ls() throws IOException{
        List<PathRecord> recordsList = new LinkedList<PathRecord>();
        if(workingDir.listFiles() != null){
            for (File file : workingDir.listFiles()){
                if(file.isDirectory()){
                    recordsList.add(new DirectoryRecord(file.getCanonicalPath()));
                } else if(file.isFile()){
                    recordsList.add(new FileRecord(file.getCanonicalPath()));
                }
            }
        }
        return recordsList;
    }

    public DirectoryRecord cd(String path) throws IOException{
        File toChange = new File(workingDir.getAbsolutePath() + "/" + path);
        if(toChange.exists() && toChange.isDirectory()){
            workingDir = toChange.getCanonicalFile();
        }
        return new DirectoryRecord(workingDir.getCanonicalPath());
    }

    public DirectoryRecord pwd() throws IOException {
        return new DirectoryRecord(workingDir.getCanonicalPath());
    }

    public IFileWriter getFileWriter() {
        return fileWriter;
    }

    public IFileReader getFileReader() {
        //TODO: future
        return null;
    }
}
