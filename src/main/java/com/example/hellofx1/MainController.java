package com.example.hellofx1;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.hellofx1.Library.books;


public class MainController implements Initializable {
    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, String> title;
    @FXML
    private TableColumn<Book,String> subtitle;
    @FXML
    private TableColumn<Book,String> isbn;
    @FXML
    private TableColumn<Book,Integer> page;
    @FXML
    private TableColumn<Book, ArrayList<String>> authors;
    @FXML
    private TableColumn<Book, ArrayList<String>> translators;
    @FXML
    private TableColumn<Book,String> publisher;
    @FXML
    private TableColumn<Book,String> covertype;
    @FXML
    private TableColumn<Book,String> edition;
    @FXML
    private TableColumn<Book, ArrayList<String>> tags;
    @FXML
    private TableColumn<Book,String> date;
    public static ObservableList<Book> list = FXCollections.observableArrayList(books);
    @FXML
    private Button AddButton;
    @FXML
    private Button searchButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        title.setCellValueFactory(new PropertyValueFactory<Book,String>("Title"));
        subtitle.setCellValueFactory(new PropertyValueFactory<Book,String>("Subtitle"));
        isbn.setCellValueFactory(new PropertyValueFactory<Book,String>("ISBN"));
        page.setCellValueFactory(new PropertyValueFactory<Book,Integer>("Page"));
        authors.setCellValueFactory(new PropertyValueFactory<Book, ArrayList<String>>("Authors"));
        translators.setCellValueFactory(new PropertyValueFactory<Book, ArrayList<String>>("Translators"));
        publisher.setCellValueFactory(new PropertyValueFactory<Book,String>("Publisher"));
        covertype.setCellValueFactory(new PropertyValueFactory<Book,String>("Covertype"));
        edition.setCellValueFactory(new PropertyValueFactory<Book,String>("Edition"));
        tags.setCellValueFactory(new PropertyValueFactory<Book,ArrayList<String>>("Tags"));
        date.setCellValueFactory(new PropertyValueFactory<Book,String>("Date"));

        bookTableView.setItems(list);

    }
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
