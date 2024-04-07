package com.example.hellofx1;

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
        stage.setTitle("FXMLLoader Example");
        stage.setScene(new Scene(fxmlLoader.load(), 1141, 653));
        stage.show();


    }
}