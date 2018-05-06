package ru.hse.kirillgolovko.simpletorrent.client.filesystems;

import ru.hse.kirillgolovko.simpletorrent.client.ClientApp;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileReader;
import ru.hse.kirillgolovko.simpletorrent.server.api.Message;
import ru.hse.kirillgolovko.simpletorrent.server.api.requests.CloseRequest;
import ru.hse.kirillgolovko.simpletorrent.server.api.requests.OpenRequest;
import ru.hse.kirillgolovko.simpletorrent.server.api.requests.ReadBlockRequest;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.*;

import java.io.IOException;
import java.net.Socket;

public class RemoteFileReader implements IFileReader {
    private final Socket socket;

    private long totalBlocks;

    private long readBlocks;

    public RemoteFileReader(Socket socket){
        this.socket = socket;
    }



    public long openFile(String path) throws IOException{
        try {
            readBlocks = 0;
            OpenRequest openRequest = new OpenRequest(path, ClientApp.getClientID());
            Response response;
            synchronized (socket){
                Message.writeMessageToOutputStream(new Message(openRequest), socket.getOutputStream());
                response = (Response) Message.getMessageFromInputStream(socket.getInputStream()).getMessageBody();
            }

            if(response instanceof OpenResponse){
                totalBlocks = ((OpenResponse) response).getTotalBlocks();
                return ((OpenResponse) response).getTotalBlocks();
            } else if (response instanceof ErrorResponse){
                throw new Exception("Server error: " + ((ErrorResponse) response).getMessage());
            } else throw new Exception("Unexpected response");

        } catch (Exception ex){
            throw new IOException(ex.getMessage());
        }
    }

    public byte[] readNextBlock() throws IOException{
        try {
            ReadBlockRequest readBlockRequest = new ReadBlockRequest(ClientApp.getClientID());
            Response response;
            synchronized (socket){
                Message.writeMessageToOutputStream(new Message(readBlockRequest), socket.getOutputStream());
                response = (Response) Message.getMessageFromInputStream(socket.getInputStream()).getMessageBody();
            }

            if(response instanceof ReadBlockResponse){
                readBlocks++;
                return ((ReadBlockResponse) response).getRawData();
            } else if (response instanceof ErrorResponse){
                throw new Exception("Server error: " + ((ErrorResponse) response).getMessage());
            } else throw new Exception("Unexpected response");

        } catch (Exception ex){
            throw new IOException(ex.getMessage());
        }
    }

    public double getReadingProgress() {
        return (double) readBlocks / (double) totalBlocks;
    }

    public long totalBlocks() {
        return totalBlocks;
    }

    public long readBlocks() {
        return readBlocks;
    }

    public void close() throws IOException{
        try {
            CloseRequest request = new CloseRequest(ClientApp.getClientID());
            Response response;
            synchronized (socket){
                Message.writeMessageToOutputStream(new Message(request), socket.getOutputStream());
                response = (Response) Message.getMessageFromInputStream(socket.getInputStream()).getMessageBody();
            }
            if(response instanceof CloseResponse){
                readBlocks = 0;
                totalBlocks = 0;
                return;
            } else if(response instanceof ErrorResponse){
                throw new IOException("Server error" + ((ErrorResponse) response).getMessage());
            } else throw new Exception("Unexpected response");

        } catch (Exception ex){
            throw new IOException(ex.getMessage());
        }
    }
}
