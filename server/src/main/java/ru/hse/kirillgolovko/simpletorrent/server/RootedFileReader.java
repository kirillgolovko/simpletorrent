package ru.hse.kirillgolovko.simpletorrent.server;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RootedFileReader implements IFileReader {
    private final long blockSize;

    private FileInputStream inputStream;

    private long totalBlocks;

    private long readBlocks;

    private RootedFilesystem filesystem;

    public RootedFileReader(long blockSize, RootedFilesystem filesystem){

        this.blockSize = blockSize;
        this.filesystem = filesystem;
    }

    @Override
    public long openFile(String path) throws IOException {
        File toRead = new File(filesystem.getWorkingDir().getCanonicalPath() + "/" + path).getCanonicalFile();
        if(toRead.exists() && toRead.isFile() && toRead.canRead() && filesystem.isSubdir(toRead.getCanonicalPath())){
            totalBlocks = toRead.length() / blockSize + (toRead.length() % blockSize == 0 ? 0 : 1);
            readBlocks = 0;
            inputStream = new FileInputStream(toRead);
            return totalBlocks;
        } else {
            throw new IOException("No such file, or can't read: " + path);
        }
    }

    @Override
    public long totalBlocks() {
        return totalBlocks;
    }



    @Override
    public long readBlocks() {
        return readBlocks;
    }

    @Override
    public double getReadingProgress() {
        return (double) readBlocks / (double) totalBlocks;
    }

    @Override
    public byte[] readNextBlock() throws IOException {
        byte[] readBuffer = new byte[(int) blockSize];
        int readBytes = inputStream.read(readBuffer);
        ++readBlocks;
        byte[] toSend = new byte[readBytes];
        for (int i = 0; i < readBytes; ++i){
            toSend[i] = readBuffer[i];
        }
        return toSend;
    }

    @Override
    public void close() throws IOException{
        if(inputStream != null){
            inputStream.close();
        }

        totalBlocks = 0;
        readBlocks = 0;
    }
}
