package com.example.hellofx1;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.stage.*;

import java.io.*;
import java.net.URL;

import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button AddButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void AddButton() {
            FXMLLoader add = new FXMLLoader(getClass().getResource("Add.fxml"));
            Stage addButton = new Stage();
            addButton.setTitle("Add");
        try {
            addButton.setScene(new Scene(add.load(), 600, 400));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addButton.show();

    }
    @FXML
    public void helpDisplay(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information !!!");
        alert.setHeaderText("about 2024");
        alert.showAndWait();
    }


}
