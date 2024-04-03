package com.example.hellofx1;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MainController {

    @FXML
    public void helpDisplay(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information !!!");
        alert.setHeaderText("about 2024");
        alert.showAndWait();
    }
}
