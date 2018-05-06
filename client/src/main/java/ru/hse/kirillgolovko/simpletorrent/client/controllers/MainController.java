package ru.hse.kirillgolovko.simpletorrent.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ru.hse.kirillgolovko.simpletorrent.client.ClientApp;
import ru.hse.kirillgolovko.simpletorrent.client.filesystems.LocalFileSystem;
import ru.hse.kirillgolovko.simpletorrent.client.filesystems.RemoteFilesystem;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.DirectoryRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.FileRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileReader;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.fileio.IFileWriter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainController {

    private ClientApp app;

    public Label serverInfoLabel;
    public Button disconnectButton;
    private Socket socket;

    @FXML
    public Button downloadButton;
    @FXML
    public VBox localBox;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public VBox serverBox;

    private DirectoryController localDirController;

    private DirectoryController remoteDirController;

    private ExecutorService executorService;

    @FXML
    public void initialize() {
        FXMLLoader localDirectoryLoader = new FXMLLoader();
        Pane localDirectoryPane = null;
        try {
            localDirectoryPane = localDirectoryLoader.load(getClass().getResourceAsStream("/fxml/directory.fxml"));
        } catch (IOException e) {

        }
        localDirController = localDirectoryLoader.getController();
        localBox.getChildren().add(localDirectoryPane);
        localDirController.setBindedFs(new LocalFileSystem(new File("").getAbsoluteFile()));

        FXMLLoader remoteDirectoryLoader = new FXMLLoader();
        Pane remotedDirectoryPane = null;
        try {
            remotedDirectoryPane = remoteDirectoryLoader.load(getClass().getResourceAsStream("/fxml/directory.fxml"));
        } catch (IOException e) {

        }
        remoteDirController = remoteDirectoryLoader.getController();
        serverBox.getChildren().add(remotedDirectoryPane);

        downloadButton.setOnAction(downloadClicked);

        disconnectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                app.setServerDiconnected();
            }
        });
        executorService = Executors.newSingleThreadExecutor();
    }

    public void setServer(Socket socket, ClientApp app) {
        this.socket = socket;
        remoteDirController.setBindedFs(new RemoteFilesystem(socket));
        remoteDirController.setExecutorService(executorService);
        serverInfoLabel.setText("Server on " + socket.getInetAddress().toString() + ":" + socket.getPort());
        this.app = app;
    }

    public void serverDisconnected() {

        remoteDirController.setBindedFs(null);
        remoteDirController.setExecutorService(null);
    }

    private EventHandler<ActionEvent> downloadClicked = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> progressBar.progressProperty().setValue(0));
            Platform.runLater(() -> downloadButton.setDisable(true));
            executorService.execute(() -> {
                if (remoteDirController.getSelected() == null
                        || remoteDirController.getSelected() instanceof DirectoryRecord) {
                    Platform.runLater(()-> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Select file");
                        alert.setHeaderText("File must be selected");
                        alert.setContentText("You can download only files");
                        alert.showAndWait();
                    });
                } else {
                    try {
                        IFileReader remoteReader = remoteDirController.getBindedFs().getFileReader();
                        IFileWriter localFileWriter = localDirController.getBindedFs().getFileWriter();
                        String fileName = ((FileRecord) remoteDirController.getSelected()).getName();
                        long blocks = remoteReader.openFile(fileName);
                        System.out.println(blocks);
                        localFileWriter.openFile(fileName);
                        for (long i = 0; i < blocks; ++i) {
                            localFileWriter.writeNextBlock(remoteReader.readNextBlock());
                            Platform.runLater(() -> progressBar.progressProperty().setValue(remoteReader.getReadingProgress()));
                        }
                        remoteReader.close();
                        localFileWriter.close();
                        localDirController.update();
                    } catch (Exception ex) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(ex.getClass().getSimpleName());
                            alert.setContentText(ex.getMessage());
                            alert.showAndWait();
                        });
                    }
                }
                Platform.runLater(()-> downloadButton.setDisable(false));
            });
        }
    };
}
