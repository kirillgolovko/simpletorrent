package ru.hse.kirillgolovko.simpletorrent.client.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.hse.kirillgolovko.simpletorrent.client.ClientApp;

import java.net.Socket;


public class ConnectDialog {
    private ClientApp app;

    public void setApp(ClientApp app) {
        this.app = app;
    }

    @FXML
    public Button connectButton;

    @FXML
    public TextField serverField;

    @FXML
    public TextField clientIdField;

    @FXML
    public TextField portField;


    @FXML
    public void initialize() {
        connectButton.setOnAction(connectPressed);
    }

    private EventHandler<ActionEvent> connectPressed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String adress = serverField.getText();
                int port = Integer.parseInt(portField.getText());
                String clientId = clientIdField.getText();

                Socket socket = new Socket(adress, port);
                app.setServerConnected(socket, clientId);
            } catch (Exception ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText(ex.getClass().getSimpleName());
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    };

}
