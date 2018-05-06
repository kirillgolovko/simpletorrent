package ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio;

import java.io.IOException;

public interface IFileWriter {
    /**
     * Opens file to write
     * @param path path
     */
    void openFile(String path) throws IOException;

    /**
     * Writes next bytes block to file
     * @param block next block
     */
    void writeNextBlock(byte[] block) throws IOException;

    /**
     * Returns how many blocks were already written
     * @return how many blocks were already written
     */
    long blocksWritten();


    /**
     * Closes file
     */
    void close() throws IOException;

}
