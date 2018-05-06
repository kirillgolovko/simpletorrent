package ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio;

import java.io.IOException;

public interface IFileReader {
    /**
     * Opens file to read
     * @param path file path
     * @return number of blocks
     */
    long openFile(String path) throws IOException;

    /**
     * Reads next block in opened file
     * @return null if file is over
     */
    byte[] readNextBlock() throws IOException;

    /**
     * Reading progress
     * @return read blocks / all blocks
     */
    double getReadingProgress();

    /**
     * File len in blocks
     * @return len in blocks
     */
    long totalBlocks();

    /**
     * How many blocks are already read
     * @return number of blocks
     */
    long readBlocks();

    /**
     * Closes file
     */
    void close() throws IOException;
}
