package ru.hse.kirillgolovko.simpletorrent.client;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ServerWhatchdog implements Runnable {

    private final ClientApp clientApp;

    public ServerWhatchdog(ClientApp clientApp) {
        this.clientApp = clientApp;
    }


    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (clientApp.isServerConnected() &&
                    ChronoUnit.SECONDS.between(clientApp.getLastServerActivity(), LocalDateTime.now()) > 2) {
                //clientApp.setServerDiconnected();
            }
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
                break;
            }
        }
    }
}
