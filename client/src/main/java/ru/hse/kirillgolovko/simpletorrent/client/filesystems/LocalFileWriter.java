package ru.hse.kirillgolovko.simpletorrent.client.filesystems;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LocalFileWriter implements IFileWriter{

    private long blocksWritten;

    private FileOutputStream outputStream;

    public void openFile(String path) throws IOException{
        File toWrite = new File(path);
        while (toWrite.exists() && toWrite.isFile()){
            toWrite = new File(toWrite.getAbsolutePath() + "1");
        }
        outputStream = new FileOutputStream(toWrite);
        blocksWritten = 0;
    }

    public void writeNextBlock(byte[] block) throws IOException{
        if (outputStream != null){
            outputStream.write(block);
            blocksWritten++;
        } else throw new IOException("First open the file");
    }

    public void close() throws IOException{
        if(outputStream != null){
            outputStream.close();
        }
    }

    public long blocksWritten() {
        return blocksWritten;
    }
}
