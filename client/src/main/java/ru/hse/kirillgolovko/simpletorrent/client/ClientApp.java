package ru.hse.kirillgolovko.simpletorrent.client;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import ru.hse.kirillgolovko.simpletorrent.client.controllers.ConnectDialog;
import ru.hse.kirillgolovko.simpletorrent.client.controllers.MainController;
import ru.hse.kirillgolovko.simpletorrent.client.filesystems.RemoteFilesystem;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.DirectoryRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.PathRecord;
import ru.hse.kirillgolovko.simpletorrent.server.api.Message;
import ru.hse.kirillgolovko.simpletorrent.server.api.requests.LsRequest;
import ru.hse.kirillgolovko.simpletorrent.server.api.responses.Response;


import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;


public class ClientApp extends Application {

    public static String getClientID() {
        return clientID;
    }

    private static String clientID;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private Stage connectionStage;

    private ConnectDialog connectionController;

    private Stage mainStage;

    private MainController mainController;

    private volatile Socket socket;

    private volatile boolean isServerConnected;

    private LocalDateTime lastServerActivity;


    private Thread pingerThread;

    private Thread whatchdogThread;


    @Override
    public void start(Stage stage) throws Exception {
        connectionStage = stage;
        String fxmlFile = "/fxml/ConnectDialog.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        connectionController = loader.getController();
        connectionController.setApp(this);
        stage.setTitle("Connect");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        FXMLLoader mainLoader = new FXMLLoader();
        Parent mainRoot = (Parent) mainLoader.load(getClass().getResourceAsStream("/fxml/mainWindow.fxml"));
        mainController = mainLoader.getController();
        mainStage = new Stage();
        mainStage.setTitle("Simpletorrent");
        mainStage.setScene(new Scene(mainRoot));
        mainStage.setResizable(false);
        mainStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });


        whatchdogThread = new Thread(new ServerWhatchdog(this));
        whatchdogThread.setDaemon(true);
        whatchdogThread.start();

    }

    public void setServerConnected(Socket socket, String clientId) {
        ClientApp.clientID = clientId;
        this.socket = socket;
        connectionStage.hide();
        lastServerActivity = LocalDateTime.now();
        pingerThread = new Thread(new ServerPinger(this, socket, clientId));
        pingerThread.setDaemon(true);
        pingerThread.start();
        isServerConnected = true;
        mainStage.show();
        mainController.setServer(socket, this);

    }

    public void setServerDiconnected(){

        pingerThread.interrupt();
        Platform.runLater(() -> connectionStage.show());
        Platform.runLater(() -> mainStage.hide());
        mainController.serverDisconnected();
        try {
            socket.close();
        }catch (Exception ex){}
        socket = null;

    }

    public void setLastServerActivity(LocalDateTime lastServerActivity) {
        this.lastServerActivity = lastServerActivity;
    }

    public LocalDateTime getLastServerActivity() {
        return lastServerActivity;
    }

    public boolean isServerConnected() {
        return isServerConnected;
    }
}