package com.example.hellofx1;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



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
    @FXML
    private TableColumn<Book,String> coverimage;

    public static ObservableList<Book> observableBookList = FXCollections.observableArrayList();

    @FXML
    private Button AddButton;
    @FXML
    private Button searchButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        title.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        subtitle.setCellValueFactory(new PropertyValueFactory<Book,String>("subtitle"));
        isbn.setCellValueFactory(new PropertyValueFactory<Book,String>("isbn"));
        page.setCellValueFactory(new PropertyValueFactory<Book,Integer>("page"));
        authors.setCellValueFactory(new PropertyValueFactory<Book, ArrayList<String>>("authors"));
        translators.setCellValueFactory(new PropertyValueFactory<Book, ArrayList<String>>("translators"));
        publisher.setCellValueFactory(new PropertyValueFactory<Book,String>("publisher"));
        covertype.setCellValueFactory(new PropertyValueFactory<Book,String>("covertype"));
        edition.setCellValueFactory(new PropertyValueFactory<Book,String>("edition"));
        tags.setCellValueFactory(new PropertyValueFactory<Book,ArrayList<String>>("tags"));
        date.setCellValueFactory(new PropertyValueFactory<Book,String>("date"));
        coverimage.setCellValueFactory(new PropertyValueFactory<Book,String>("coverImage"));

        title.setCellFactory(TextFieldTableCell.forTableColumn());
        subtitle.setCellFactory(TextFieldTableCell.forTableColumn());
        isbn.setCellFactory(TextFieldTableCell.forTableColumn());

        title.setCellFactory(TextFieldTableCell.forTableColumn());
        title.setCellFactory(TextFieldTableCell.forTableColumn());
        title.setCellFactory(TextFieldTableCell.forTableColumn());
        title.setCellFactory(TextFieldTableCell.forTableColumn());
        title.setCellFactory(TextFieldTableCell.forTableColumn());
        title.setCellFactory(TextFieldTableCell.forTableColumn());


        bookTableView.setItems(observableBookList);
        bookTableView.setEditable(true);

    }
    /*/ handle column edits*/
    public void titleCol_OnEdit(Event e){
        TableColumn.CellEditEvent<Book,String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Book,String>) e;
        Book book = cellEditEvent.getRowValue();
        book.setTitle(cellEditEvent.getNewValue());
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
    public void DeleteButton(ActionEvent event){
        if(!observableBookList.isEmpty()){
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING,"Confirm", ButtonType.OK,ButtonType.CANCEL);
            deleteAlert.setContentText("Are you sure you want to delete this?\n\n THIS CANNOT BE UNDONE.");
            deleteAlert.initModality(Modality.APPLICATION_MODAL);
            deleteAlert.showAndWait();
            if(deleteAlert.getResult() == ButtonType.OK){
                observableBookList.removeAll(bookTableView.getSelectionModel().getSelectedItems());
                bookTableView.getSelectionModel().clearSelection();
            } else{
                deleteAlert.close();
            }
        }
    }


    @FXML
    public void helpDisplay(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information !!!");
        alert.setHeaderText("about 2024");
        alert.showAndWait();
    }



}
