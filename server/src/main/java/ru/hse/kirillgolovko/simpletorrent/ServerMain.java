package ru.hse.kirillgolovko.simpletorrent;

import ru.hse.kirillgolovko.simpletorrent.appparams.AppParams;
import ru.hse.kirillgolovko.simpletorrent.socketprocessor.SocketProcessorThread;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    private static AppParams params;

    private static ExecutorService socketProcessorsPool;

    public static  void main(String[] args){
        // читаем параметры приложения
        try {
            JAXBContext context = JAXBContext.newInstance(AppParams.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JAXBElement<AppParams> root = unmarshaller.unmarshal(new StreamSource(new File(args[0])), AppParams.class);
            params = root.getValue();
        } catch (Exception ex){
            System.err.println("Error loading params: " + ex.getMessage());
            System.exit(1);
        }
        // создаем обработчик для запросов
        socketProcessorsPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()
                * params.getMaxClients());
        // создаем сервер

        ServerSocket server = null;

        try {
            server = new ServerSocket(params().getServerPort());
        } catch (IOException ex){
            StringBuilder message = new StringBuilder();
            message.append("Can't open ");
            message.append(params().getServerPort());
            message.append(" port. Reason : ");
            message.append(ex.getMessage());
            System.err.println(message.toString());
            System.exit(1);
        }
        // ждем соединения
        while (true){
            try {
                Socket nextSocket = server.accept();
                socketProcessorsPool.execute(new SocketProcessorThread(nextSocket));
            } catch (Exception ex){
                System.err.println("Error processing client " + ex.getMessage());
            }

        }
    }

    public static AppParams params(){
        return params;
    }
}
