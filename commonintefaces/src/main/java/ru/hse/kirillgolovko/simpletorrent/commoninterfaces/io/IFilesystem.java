package ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io;

import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.DirectoryRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.PathRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileReader;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileWriter;

import java.io.IOException;
import java.util.List;

/**
 * Interface for describing basic filesystem, contains file reader/writer, cd and ls commands
 *
 */
public interface IFilesystem {
    /**
     * File reader instance
     * @return file reader to read files binary from current filesystem
     */
    IFileReader getFileReader();

    /**
     * File writer instance
     * @return file writer to write files binary in current filesystem
     */
    IFileWriter getFileWriter();

    /**
     * changes directory
     * @param path where
     * @return new directory path
     */
    DirectoryRecord cd(String path) throws IOException;

    /**
     * Lists current directory
     * @return List of path records in current directory
     */
    List<PathRecord> ls() throws IOException;

    /**
     * Returns current directory
     * @return current directory record
     */
    DirectoryRecord pwd() throws IOException;

}
