package ru.hse.kirillgolovko.simpletorrent.client.filesystems;

import ru.hse.kirillgolovko.simpletorrent.client.ClientApp;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.DirectoryRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.PathRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileReader;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileWriter;
import ru.hse.kirillgolovko.simpletorrent.server.api.Message;
import ru.hse.kirillgolovko.simpletorrent.server.api.requests.CdRequest;
import ru.hse.kirillgolovko.simpletorrent.server.api.requests.LsRequest;
import ru.hse.kirillgolovko.simpletorrent.server.api.requests.PwdRequest;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class RemoteFilesystem implements IFilesystem{

    private final  Socket server;

    private final RemoteFileReader reader;

    public RemoteFilesystem(Socket socket) {
        server = socket;
        reader = new RemoteFileReader(socket);
    }

    public List<PathRecord> ls() throws IOException {
        try{
            LsRequest lsRequest = new LsRequest(ClientApp.getClientID());
            Response response;
            synchronized (server){
                Message.writeMessageToOutputStream(new Message(lsRequest), server.getOutputStream());
                response = (Response) Message.getMessageFromInputStream(server.getInputStream()).getMessageBody();
            }
            if(response instanceof LsResponse){
                return ((LsResponse) response).getRecords();
            } else if (response instanceof ErrorResponse){
                throw new Exception("Server error: " + ((ErrorResponse) response).getMessage());
            } else {
                throw new Exception("Unexpected response type for ls: " + response.getClass().getSimpleName());
            }
        } catch (Exception ex){
            throw new IOException(ex.getMessage());
        }
    }

    public DirectoryRecord cd(String path) throws IOException{
        try{
            CdRequest cdRequest = new CdRequest(path, ClientApp.getClientID());
            Response response;
            synchronized (server) {
                Message.writeMessageToOutputStream(new Message(cdRequest), server.getOutputStream());
                response = (Response) Message.getMessageFromInputStream(server.getInputStream()).getMessageBody();
            }
            if(response instanceof CdResponse){
                return ((CdResponse) response).getDirectoryRecord();
            } else if (response instanceof ErrorResponse){
                throw new Exception("Server error: " + ((ErrorResponse) response).getMessage());
            } else {
                throw new Exception("Unexpected response type for ls: " + response.getClass().getSimpleName());
            }
        } catch (Exception ex){
            throw new IOException(ex.getMessage());
        }
    }

    public DirectoryRecord pwd() throws IOException {
        try{
            PwdRequest pwdRequest = new PwdRequest(ClientApp.getClientID());
            Response response;
            synchronized (server) {
                Message.writeMessageToOutputStream(new Message(pwdRequest), server.getOutputStream());
                response = (Response) Message.getMessageFromInputStream(server.getInputStream()).getMessageBody();
            }
            if(response instanceof PwdResponse){
                return ((PwdResponse) response).getPath();
            } else if (response instanceof ErrorResponse){
                throw new Exception("Server error: " + ((ErrorResponse) response).getMessage());
            } else {
                throw new Exception("Unexpected response type for ls: " + response.getClass().getSimpleName());
            }
        } catch (Exception ex){
            throw new IOException(ex.getMessage());
        }
    }

    public IFileReader getFileReader() {
        return reader;
    }

    public IFileWriter getFileWriter() {
        return null;
    }
}
