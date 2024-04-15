package com.example.Controllers;


import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
            File folder = new File("Mylibrary/books/");
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        System.out.println("Reading file: " + file.getAbsolutePath()); // Debug
                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                            Gson gson = new Gson();
                            Book book = gson.fromJson(br, Book.class);
                            observableBookList.add(book);
                            tempResults.add(book);
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
        language.setCellValueFactory(new PropertyValueFactory<Book, String>("language"));
        rating.setCellValueFactory(new PropertyValueFactory<Book, Double>("rating"));

        // loadBooksFromJson();
        bookTableView.setItems(observableBookList);


    }

    @FXML
    public void SearchButton() {
        FXMLLoader search = new FXMLLoader(getClass().getResource("Search.fxml"));
        Stage searchButton = new Stage();
        searchButton.setTitle("Search");
        try {
            searchButton.setScene(new Scene(search.load(), 476, 482));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchButton.setResizable(false);
        searchButton.show();


    }

    @FXML
    public void DeleteButton(ActionEvent event) {
        if (!observableBookList.isEmpty()) {
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Confirm", ButtonType.OK, ButtonType.CANCEL);
            deleteAlert.setContentText("Are you sure you want to delete this?\n\n THIS CANNOT BE UNDONE.");
            deleteAlert.initModality(Modality.APPLICATION_MODAL);
            deleteAlert.showAndWait();

            if (deleteAlert.getResult() == ButtonType.OK) {
                ArrayList<Book> selectedBooks = new ArrayList<>(bookTableView.getSelectionModel().getSelectedItems());
                observableBookList.removeAll(bookTableView.getSelectionModel().getSelectedItems());
                for (Book selectedBook : selectedBooks) {
                    deleteSelectedBook(selectedBook);
                }
                bookTableView.getSelectionModel().clearSelection();
                tempResults.clear();
                tempResults.addAll(observableBookList);
            } else {
                deleteAlert.close();
            }
        }
    }


    public void deleteSelectedBook(Book selectedBook) {

        String folderPath = "Mylibrary/books";

        String filePath = folderPath + "/" + selectedBook.getIsbn() + ".json";
        String imagePath = selectedBook.getCoverImage();

        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println(filePath + " başarıyla silindi.");


                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        if (imageFile.delete()) {
                            System.out.println(imagePath + " başarıyla silindi.");
                        } else {
                            System.out.println(imagePath + " silinemedi.");
                        }
                    } else {
                        System.out.println("Belirtilen resim dosyası bulunamadı.");
                    }
                }

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
            addButton.setScene(new Scene(add.load(), 655, 719));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addButton.setResizable(false);

        addButton.show();
    }

    @FXML
    public void editButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit.fxml"));
        Parent root = loader.load();

        Book editbook = bookTableView.getSelectionModel().getSelectedItem();

        EditController editController = loader.getController();
        editController.showInformation(editbook);


        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Edit Book");
        stage.show();


    }

    @FXML
    public void helpDisplay() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome to the Library Management System");
        alert.setHeaderText("Library Management System User Guide");
        alert.setContentText("Add Book: Use the \"Add\" function to input details for a new book and store it in the system.\n" +
                "Delete Book: Choose a book or books and select \"Delete\" to remove it from the library.\n" +
                "Edit Book: Update book details by selecting the book to \"Edit\" information.\n" +
                "Search for Book: Utilize search functionality to locate books based on title, author,ISBN or etc.\n" +
                "Update: After searching, return to the list where you have started.\n"+
                "Do not worry all the information of books are stored in the program");

        alert.showAndWait();
    }

    public void UpdateButton() {
        observableBookList.clear();
        observableBookList.addAll(tempResults);
    }


}
