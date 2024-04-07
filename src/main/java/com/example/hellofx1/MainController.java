package com.example.hellofx1;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.*;
import java.io.*;


public class MainController  {
    @FXML
    private Button AddButton;
    @FXML
    private Button searchButton;
    @FXML
    public void SearchButton() {
        FXMLLoader search = new FXMLLoader(getClass().getResource("Search.fxml"));
        Stage searchButton = new Stage();
        searchButton.setTitle("Search");
        try {
            searchButton.setScene(new Scene(search.load(), 654, 466));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchButton.setResizable(false);

        searchButton.show();

    }

    @FXML
    public void AddButton() {
            FXMLLoader add = new FXMLLoader(getClass().getResource("Add.fxml"));
            Stage addButton = new Stage();
            addButton.setTitle("Add");
        try {
            addButton.setScene(new Scene(add.load(), 654, 466));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addButton.setResizable(false);

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
