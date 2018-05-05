package ru.hse.kirillgolovko.simpletorrent;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ru.hse.kirillgolovko.simpletorrent.api.Message;
import ru.hse.kirillgolovko.simpletorrent.api.requests.IsUpRequest;
import ru.hse.kirillgolovko.simpletorrent.api.requests.Request;
import ru.hse.kirillgolovko.simpletorrent.api.responses.Response;


import java.io.PrintWriter;
import java.net.Socket;


public class Main extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/main.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("JavaFX and Maven");
        stage.setScene(new Scene(root));
        stage.show();
        Socket socket = new Socket("localhost" , 8080);
        IsUpRequest isUpRequest = new IsUpRequest("Is Up?");
        Message.writeMessageToOutputStream(new Message(new IsUpRequest("Is Up?")), socket.getOutputStream());
        Message answer = Message.getMessageFromInputStream(socket.getInputStream());
        System.out.print("Ok");


    }
}