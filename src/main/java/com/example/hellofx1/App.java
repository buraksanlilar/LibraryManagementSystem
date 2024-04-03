package com.example.hellofx1;

import com.google.gson.Gson;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.*;
import java.nio.file.Files;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));

        stage.setTitle("FXMLLoader Example");
        stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
        stage.show();

    }
}