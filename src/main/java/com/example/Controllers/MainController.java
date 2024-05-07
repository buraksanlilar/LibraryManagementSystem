package com.example.Controllers;


import com.google.gson.*;
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
import java.util.Arrays;
import java.util.ResourceBundle;

import static com.example.Controllers.SearchController.searched;
import static com.example.Controllers.AddController.*;


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
    private TableColumn<Book, String> authors;
    @FXML
    private TableColumn<Book, String> translators;
    @FXML
    private TableColumn<Book, String> publisher;
    @FXML
    private TableColumn<Book, String> edition;
    @FXML
    private TableColumn<Book, String> language;
    @FXML
    private TableColumn<Book, Double> rating;
    @FXML
    private TableColumn<Book, String> tags;
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
    private Window stage;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadBooksFromJson();


        title.setCellValueFactory(new PropertyValueFactory<Book, String>("Title"));
        subtitle.setCellValueFactory(new PropertyValueFactory<Book, String>("Subtitle"));
        isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("Isbn"));
        authors.setCellValueFactory(new PropertyValueFactory<Book, String>("Authors"));
        translators.setCellValueFactory(new PropertyValueFactory<Book, String>("Translators"));
        publisher.setCellValueFactory(new PropertyValueFactory<Book, String>("Publisher"));
        edition.setCellValueFactory(new PropertyValueFactory<Book, String>("Edition"));
        tags.setCellValueFactory(new PropertyValueFactory<Book, String>("Tags"));
        date.setCellValueFactory(new PropertyValueFactory<Book, String>("Date"));
        language.setCellValueFactory(new PropertyValueFactory<Book, String>("language"));
        rating.setCellValueFactory(new PropertyValueFactory<Book, Double>("rating"));


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
        if (bookTableView.getSelectionModel().getSelectedItem() == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR, "Back", ButtonType.CLOSE);
            nullAlert.setContentText("You need to select a book to delete!");
            nullAlert.initModality(Modality.WINDOW_MODAL);
            nullAlert.showAndWait();
        } else {
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

                    if (searched) {
                        tempResults.removeAll(selectedBooks);
                    }

                    bookTableView.getSelectionModel().clearSelection();

                    if (!searched) {
                        tempResults.clear();
                        tempResults.addAll(observableBookList);
                    }


                } else {
                    deleteAlert.close();
                }
            }
        }
    }


    public void deleteSelectedBook(Book selectedBook) {

        String folderPath = "Mylibrary/books";

        String filePath = folderPath + "/" + selectedBook.getIsbn() + ".json";
        String imagePath = selectedBook.getCover();

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

        if (bookTableView.getSelectionModel().getSelectedItem() == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR, "Back", ButtonType.CLOSE);
            nullAlert.setContentText("You need to select a book to edit!!");
            nullAlert.initModality(Modality.WINDOW_MODAL);
            nullAlert.showAndWait();
        } else {
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
                "In the search function there is a reset button and resets the current table before any search.\n" +
                "Do not worry all the information of books are stored in the program\n" +
                "You can Export the selected books with the File->Export button\n" +
                "Also you can select any books directory with File->Import button. It will change the current table to the selected directory");

        alert.showAndWait();
    }

    public void loadBooksFromJson() {
        try {
            File folder = new File("Mylibrary/books/");
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        System.out.println("Reading file: " + file.getAbsolutePath());
                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                            Gson gson = new Gson();
                            JsonObject jsonObject = gson.fromJson(br, JsonObject.class);
                            Book loadbook = new Book();

                            JsonArray authorsArray = jsonObject.getAsJsonArray("authors");
                            StringBuilder authorsStringBuilder = new StringBuilder();
                            for (JsonElement element : authorsArray) {
                                authorsStringBuilder.append(element.getAsString()).append(", ");
                            }
                            String authorsString = authorsStringBuilder.toString().trim();

                            JsonArray tagsArray = jsonObject.getAsJsonArray("tags");
                            StringBuilder tagsStringBuilder = new StringBuilder();
                            for (JsonElement element : tagsArray) {
                                tagsStringBuilder.append(element.getAsString()).append(", ");
                            }
                            String tagsString = tagsStringBuilder.toString().trim();

                            JsonArray translatorsArray = jsonObject.getAsJsonArray("translators");
                            StringBuilder translatorsStringBuilder = new StringBuilder();
                            for (JsonElement element : translatorsArray) {
                                translatorsStringBuilder.append(element.getAsString()).append(", ");
                            }
                            String translatorsString = translatorsStringBuilder.toString().trim();


                            loadbook.setAuthors(authorsString);
                            loadbook.setTranslators(translatorsString);
                            loadbook.setTags(tagsString);
                            loadbook.setTitle(jsonObject.get("title").getAsString());
                            loadbook.setSubtitle(jsonObject.get("subtitle").getAsString());
                            loadbook.setLanguage(jsonObject.get("language").getAsString());
                            loadbook.setIsbn(jsonObject.get("isbn").getAsString());
                            loadbook.setPublisher(jsonObject.get("publisher").getAsString());
                            loadbook.setDate(jsonObject.get("date").getAsString());
                            loadbook.setEdition(jsonObject.get("edition").getAsString());
                            loadbook.setCover(jsonObject.get("cover").getAsString());
                            loadbook.setRating(jsonObject.get("rating").getAsDouble());

                            observableBookList.add(loadbook);
                            tempResults.add(loadbook);

                        } catch (IOException e) {
                            System.err.println("Error reading file: " + file.getAbsolutePath());
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

    private void importSelected(String filepath) {
        try {
            File folder = new File(filepath);
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        System.out.println("Reading file: " + file.getAbsolutePath());
                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                            Gson gson = new Gson();
                            JsonObject jsonObject = gson.fromJson(br, JsonObject.class);
                            Book loadbook = new Book();

                            JsonArray authorsArray = jsonObject.getAsJsonArray("authors");
                            StringBuilder authorsStringBuilder = new StringBuilder();
                            for (JsonElement element : authorsArray) {
                                authorsStringBuilder.append(element.getAsString()).append(", ");
                            }
                            String authorsString = authorsStringBuilder.toString().trim();

                            JsonArray tagsArray = jsonObject.getAsJsonArray("tags");
                            StringBuilder tagsStringBuilder = new StringBuilder();
                            for (JsonElement element : tagsArray) {
                                tagsStringBuilder.append(element.getAsString()).append(", ");
                            }
                            String tagsString = tagsStringBuilder.toString().trim();

                            JsonArray translatorsArray = jsonObject.getAsJsonArray("translators");
                            StringBuilder translatorsStringBuilder = new StringBuilder();
                            for (JsonElement element : translatorsArray) {
                                translatorsStringBuilder.append(element.getAsString()).append(", ");
                            }
                            String translatorsString = translatorsStringBuilder.toString().trim();


                            loadbook.setAuthors(authorsString);
                            loadbook.setTranslators(translatorsString);
                            loadbook.setTags(tagsString);
                            loadbook.setTitle(jsonObject.get("title").getAsString());
                            loadbook.setSubtitle(jsonObject.get("subtitle").getAsString());
                            loadbook.setLanguage(jsonObject.get("language").getAsString());
                            loadbook.setIsbn(jsonObject.get("isbn").getAsString());
                            loadbook.setPublisher(jsonObject.get("publisher").getAsString());
                            loadbook.setDate(jsonObject.get("date").getAsString());
                            loadbook.setEdition(jsonObject.get("edition").getAsString());
                            loadbook.setCover(jsonObject.get("cover").getAsString());
                            loadbook.setRating(jsonObject.get("rating").getAsDouble());



                            observableBookList.add(loadbook);
                            tempResults.add(loadbook);
                            saveBookInfoToJson(loadbook);
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + file.getAbsolutePath());
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

    @FXML
    public void exportButton(ActionEvent event) {
        if (!observableBookList.isEmpty()) {
            Alert exportAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm", ButtonType.OK, ButtonType.CANCEL);
            exportAlert.setContentText("Are you sure you want to export these books?\n\n");
            exportAlert.initModality(Modality.APPLICATION_MODAL);
            exportAlert.showAndWait();

            if (exportAlert.getResult() == ButtonType.OK) {
                ArrayList<Book> selectedBooks = new ArrayList<>(bookTableView.getSelectionModel().getSelectedItems());
                DirectoryChooser dc = new DirectoryChooser();
                dc.setTitle("Select folder to save books");
                File selectedDirectory = dc.showDialog(stage);

                for (Book selectedBook : selectedBooks) {
                    exportAsJSON(selectedBook, selectedDirectory);
                }

                bookTableView.getSelectionModel().clearSelection();
            } else {
                exportAlert.close();
            }
        }
    }

    public void exportAsJSON(Book selectedBook, File selectedDirectory) {
        if (selectedDirectory != null) {
            String folderPath = selectedDirectory.getAbsolutePath() + "/ExportedBooks";
            File folder = new File(folderPath);

            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    System.out.println("Folder created: " + folderPath);
                } else {
                    System.out.println("Failed to create folder: " + folderPath);
                    return; // Exit the method if folder creation fails
                }
            }
            String filePath = folderPath + "/" + selectedBook.getIsbn() + ".json";
            try (FileWriter writer = new FileWriter(filePath)) {

                JsonObject bookJson = new JsonObject();
                bookJson.addProperty("title", selectedBook.getTitle());
                bookJson.addProperty("subtitle", selectedBook.getSubtitle());
                bookJson.addProperty("isbn", selectedBook.getIsbn());

                JsonArray authorsArray = new JsonArray();
                for (String author : selectedBook.getAuthors().split(",")) {
                    authorsArray.add(author);
                }
                bookJson.add("authors", authorsArray);

                //
                JsonArray translatorsArray = new JsonArray();
                for (String translator : selectedBook.getTranslators().split(",")) {
                    translatorsArray.add(translator);
                }
                bookJson.add("translators", translatorsArray);

                JsonArray tagsArray = new JsonArray();
                for (String tag : selectedBook.getTags().split(",")) {
                    tagsArray.add(tag);
                }
                bookJson.add("tags", tagsArray);

                bookJson.addProperty("publisher", selectedBook.getPublisher());
                bookJson.addProperty("date", selectedBook.getDate());
                bookJson.addProperty("edition", selectedBook.getEdition());
                bookJson.addProperty("cover", selectedBook.getCover());
                bookJson.addProperty("rating", selectedBook.getRating());
                bookJson.addProperty("language", selectedBook.getLanguage());
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonString = gson.toJson(bookJson);
                writer.write(jsonString);

                System.out.println("Book exported as JSON: " + filePath);
            } catch (IOException e) {
                System.err.println("Error exporting book as JSON: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void importAsJSON() {
        try {
            DirectoryChooser dc = new DirectoryChooser();
            dc.setTitle("Select folder to open!");
            File folder = dc.showDialog(stage);

            importSelected(folder.getAbsolutePath());
        }catch (Exception e){
            System.out.println("Directory cannot opened");
        }
    }
}





