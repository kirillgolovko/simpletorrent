package ru.hse.kirillgolovko.simpletorrent.client.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.IFilesystem;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.DirectoryRecord;
import ru.hse.kirillgolovko.simpletorrent.commoninterfaces.io.commontypes.PathRecord;


import javax.xml.soap.Text;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;


public class DirectoryController {
    public Pane rootPane;
    private IFilesystem bindedFs;

    @FXML
    public Button upButton;

    @FXML
    public Label pathLabel;

    @FXML
    public ListView<PathRecord> filesList;

    ExecutorService executorService;

    public void initialize() {
        rootPane.setPrefSize(400, 650);
        filesList.setPrefSize(400, 550);
        //filesList.setMinSize(200, 200);
        upButton.setOnAction(upPressed);
        filesList.setOnMouseClicked(fileClicked);

    }

    public IFilesystem getBindedFs() {
        return bindedFs;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setBindedFs(IFilesystem bindedFs) {
        this.bindedFs = bindedFs;
        update();
    }

    public void update() {
        Platform.runLater(filesList.getItems()::clear);
        Platform.runLater(() -> pathLabel.setText(""));
        if (executorService != null) {
            executorService.execute(updateCall);
        } else {
            updateCall.run();
        }
    }

    public PathRecord getSelected() {
        return filesList.getSelectionModel().getSelectedItem();
    }

    private EventHandler<ActionEvent> upPressed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (executorService != null) {
                executorService.execute(upClickedCall);
            } else {
                upClickedCall.run();
            }
        }
    };


    private EventHandler<MouseEvent> fileClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (executorService != null) {
                executorService.execute(fileClickedCall);
            } else {
                fileClickedCall.run();
            }
        }
    };

    private Runnable updateCall = new Runnable() {
        @Override
        public void run() {
            try {
                if (bindedFs != null) {
                    ObservableList<PathRecord> listOfRecords = FXCollections.observableArrayList();
                    listOfRecords.addAll(bindedFs.ls());
                    Platform.runLater(() -> filesList.setItems(listOfRecords));
                    String newPwd = bindedFs.pwd().getPath();
                    Platform.runLater(() -> pathLabel.setText(newPwd));
                }
            } catch (IOException ex) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("IO Error while getting files list");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                });
            }
        }
    };

    private Runnable fileClickedCall = new Runnable() {
        @Override
        public void run() {
            PathRecord selectedFile = getSelected();
            if (selectedFile instanceof DirectoryRecord) {
                if (bindedFs != null) {
                    try {
                        bindedFs.cd(((DirectoryRecord) selectedFile).getName());
                        update();
                    } catch (IOException ex) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("IO Error while changing Directory");
                            alert.setContentText(ex.getMessage());
                            alert.showAndWait();
                        });
                    }
                }
            }
        }
    };

    private Runnable upClickedCall = new Runnable() {
        @Override
        public void run() {
            if (bindedFs != null) {
                try {
                    bindedFs.cd("..");
                    update();
                } catch (IOException ex) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("IO Error while changing Directory");
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait();
                    });
                }
            }
        }
    };


}



