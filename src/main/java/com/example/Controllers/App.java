package com.example.Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        stage.setTitle("Library Management System");
        stage.setScene(new Scene(fxmlLoader.load(), 1141, 653));
        stage.show();
        stage.setResizable(true);


    }
}