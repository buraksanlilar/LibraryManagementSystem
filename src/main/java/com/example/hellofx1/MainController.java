package com.example.hellofx1;


import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController {
    @FXML
    private Stage stage;
    @FXML
    private ListView<Book> listView;
    @FXML
    public void saveToJson(ActionEvent event) {
        // Get the list of books from the ListView
        ObservableList<Book> items = listView.getItems();

        // Convert ObservableList to ArrayList (optional, depends on your implementation)
        List<Book> books = new ArrayList<>(items);

        // Show file chooser dialog to select where to save the JSON file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save JSON File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        Stage stage = (Stage) listView.getScene().getWindow();
        java.io.File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // Save the list of books as JSON to the selected file
            saveBooksToJson(books, file);
        }
    }

    private void saveBooksToJson(List<Book> books, java.io.File file) {
        Gson gson = new Gson();
        String json = gson.toJson(books);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed, such as showing an error dialog
        }
    }
    @FXML
    public ListView<Book> openFileJSON(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JSON File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try (Reader reader = new FileReader(selectedFile)) {
                Gson gson = new Gson();
                Book[] books = gson.fromJson(reader,Book[].class);
                if (books != null) {
                    List<Book> booksToList = Arrays.asList(books);
                    ObservableList<Book> observableList = FXCollections.observableArrayList(booksToList);
                    return new ListView<>(observableList);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @FXML
    public void helpDisplay(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information !!!");
        alert.setHeaderText("about 2024");
        alert.showAndWait();
    }

}
