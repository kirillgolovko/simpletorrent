package ru.hse.kirillgolovko.simpletorrent.server;

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

public class RootedFilesystem implements IFilesystem {

    private RootedFileReader rootedFileReader;

    private File rootDirectory;

    private File workingDir;

    public File getWorkingDir() {
        return workingDir;
    }

    public RootedFilesystem(String rootDirectoryPath) throws IOException {
        rootDirectory = new File(rootDirectoryPath).getAbsoluteFile();
        if (!(rootDirectory.exists() && rootDirectory.isDirectory())) {
            throw new IOException("No such directory" + rootDirectoryPath);
        }
        workingDir = rootDirectory;
        rootedFileReader = new RootedFileReader(512 * ServerMain.params().getBlocksPerTransmission(), this);
    }

    @Override
    public List<PathRecord> ls() {
        List<PathRecord> pathRecords = new LinkedList<>();
        if (workingDir.listFiles() != null) {
            for (File file : workingDir.listFiles()) {
                if (file.isFile()) {
                    FileRecord next = new FileRecord(file.getAbsolutePath());
                    next.setPath(getRelativePath(file.getAbsolutePath()));
                    pathRecords.add(next);
                } else if (file.isDirectory()) {
                    DirectoryRecord next = new DirectoryRecord(getRelativePath(file.getPath()));
                    pathRecords.add(next);
                }
            }
        }
        return pathRecords;
    }

    @Override
    public DirectoryRecord cd(String path) throws IOException{
        File pathToChange = new File(workingDir.getCanonicalPath() + "/" + path);
        if(pathToChange.exists() && isSubdir(pathToChange.getCanonicalPath())){
            workingDir = pathToChange.getCanonicalFile();
        }
        return new DirectoryRecord(getRelativePath(workingDir.getCanonicalFile().getAbsolutePath()));
    }

    @Override
    public DirectoryRecord pwd() {
        return new DirectoryRecord(getRelativePath(workingDir.getAbsolutePath()));
    }

    @Override
    public IFileReader getFileReader() {
        return rootedFileReader;
    }

    @Override
    public IFileWriter getFileWriter() {
        // TODO: in future
        return null;
    }

    private String getRelativePath(String path) {
        return rootDirectory.toURI().relativize(new File(path).toURI()).toString();
    }

    boolean isSubdir(String path) throws IOException{
        File toCheck = new File(path);
        if(toCheck.getCanonicalPath().equals(rootDirectory.getCanonicalPath()))
            return true;
        while (toCheck.getParentFile() != null && !toCheck.getParentFile().getCanonicalPath().equals(rootDirectory.getCanonicalPath()))
            toCheck = toCheck.getParentFile();
        if(toCheck.getParentFile() != null &&  toCheck.getParentFile().getCanonicalPath().equals(rootDirectory.getCanonicalPath()))
            return true;
        else
            return false;
    }


}
