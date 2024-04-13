package com.example.hellofx1;


import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<Book, String> subtitle;
    @FXML
    private TableColumn<Book, String> isbn;
    @FXML
    private TableColumn<Book, Integer> page;
    @FXML
    private TableColumn<Book, ArrayList<String>> authors;
    @FXML
    private TableColumn<Book, ArrayList<String>> translators;
    @FXML
    private TableColumn<Book, String> publisher;
    @FXML
    private TableColumn<Book, String> covertype;
    @FXML
    private TableColumn<Book, String> edition;
    @FXML
    private TableColumn<Book, String> language;
    @FXML
    private TableColumn<Book, Double> rating;
    @FXML
    private TableColumn<Book, ArrayList<String>> tags;
    @FXML
    private TableColumn<Book, String> date;
    public static ObservableList<Book> observableBookList = FXCollections.observableArrayList();

    @FXML
    private Button AddButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button UpdateButton;
    static ArrayList<Book> tempResults = new ArrayList<>();

    public void loadBooksFromJson() {
        try {
            File folder = new File("books");
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        System.out.println("Reading file: " + file.getAbsolutePath()); // Debug
                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                            Gson gson = new Gson();
                            Book book = gson.fromJson(br, Book.class);
                            observableBookList.add(book);
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + file.getAbsolutePath()); // Debug
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Unexpected error: " + ex.getMessage()); // Debug
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadBooksFromJson();

        title.setCellValueFactory(new PropertyValueFactory<Book, String>("Title"));
        subtitle.setCellValueFactory(new PropertyValueFactory<Book, String>("Subtitle"));
        isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("Isbn"));
        page.setCellValueFactory(new PropertyValueFactory<Book, Integer>("Page"));
        authors.setCellValueFactory(new PropertyValueFactory<Book, ArrayList<String>>("Authors"));
        translators.setCellValueFactory(new PropertyValueFactory<Book, ArrayList<String>>("Translators"));
        publisher.setCellValueFactory(new PropertyValueFactory<Book, String>("Publisher"));
        covertype.setCellValueFactory(new PropertyValueFactory<Book, String>("Covertype"));
        edition.setCellValueFactory(new PropertyValueFactory<Book, String>("Edition"));
        tags.setCellValueFactory(new PropertyValueFactory<Book, ArrayList<String>>("Tags"));
        date.setCellValueFactory(new PropertyValueFactory<Book, String>("Date"));
        language.setCellValueFactory(new PropertyValueFactory<Book,String>("language"));
        rating.setCellValueFactory(new PropertyValueFactory<Book,Double>("rating"));

        // loadBooksFromJson();
        bookTableView.setItems(observableBookList);


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
    public void DeleteButton(ActionEvent event){
        if(!observableBookList.isEmpty()){
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING,"Confirm", ButtonType.OK,ButtonType.CANCEL);
            deleteAlert.setContentText("Are you sure you want to delete this?\n\n THIS CANNOT BE UNDONE.");
            deleteAlert.initModality(Modality.APPLICATION_MODAL);
            deleteAlert.showAndWait();

            if(deleteAlert.getResult() == ButtonType.OK){
                ArrayList<Book> selectedBooks = new ArrayList<>(bookTableView.getSelectionModel().getSelectedItems());
                observableBookList.removeAll(bookTableView.getSelectionModel().getSelectedItems());
                for (Book selectedBook : selectedBooks) {
                    deleteSelectedBook(selectedBook);
                }
                bookTableView.getSelectionModel().clearSelection();
                tempResults.clear();
                tempResults.addAll(observableBookList);
            } else{
                deleteAlert.close();
            }
        }

    }


    public void deleteSelectedBook(Book selectedBook) {

        String folderPath = "books";

        String filePath = folderPath + File.separator + selectedBook.getTitle() + ".json";
        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println(filePath + " başarıyla silindi.");
            } else {
                System.out.println(filePath + " silinemedi.");
            }
        } else {
            System.out.println("Belirtilen JSON dosya bulunamadı.");
        }



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
    public void helpDisplay() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information !!!");
        alert.setHeaderText("about 2024");
        alert.showAndWait();
    }
    public void UpdateButton() {
        observableBookList.clear();
        observableBookList.addAll(tempResults);
        //   bookTableView.setItems(observableBookList);


    }


}