package com.example.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import javafx.scene.image.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;


import static com.example.Controllers.MainController.*;


public class AddController {
    @FXML
    private TextField title;
    @FXML
    private TextField subtitle;
    @FXML
    private TextField isbn;
    @FXML
    private TextField authors;
    @FXML
    private TextField translators;
    @FXML
    private TextField publisher;
    @FXML
    private DatePicker date;
    @FXML
    private TextField edition;
    @FXML
    private TextField tag;
    @FXML
    private Button resetButton;
    @FXML
    private Button addButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button imageButton;
    private String imagePath = "";
    @FXML
    private Rating bookRating;
    @FXML
    private TextField language;
    @FXML
    private ImageView imageView;
    private File imageFile;

    @FXML
    public void selectImage(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a image");


        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);


        Stage stage = (Stage) imageButton.getScene().getWindow();
        imageFile = fileChooser.showOpenDialog(stage);


        if (imageFile != null) {
            Image image = new Image(imageFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void addImage(){

        String imagesDirectory = "MyLibrary/images/";
        File imagesDir = new File(imagesDirectory);

        if (!imagesDir.exists()) {
            try {
                imagesDir.mkdirs();
            } catch (SecurityException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error creating images directory.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }
        if(imageFile != null){
            String imageName = imageFile.getName();
            String targetPath = imagesDirectory + imageName;
            try {
                Files.copy(imageFile.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error copying image file.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            imagePath = targetPath;
        }
    }


    @FXML
    public void AddNewBook(){
        Book newbook = new Book();
        if(checkNull() ){
            Alert nullAlert = new Alert(Alert.AlertType.INFORMATION,"Back",ButtonType.CANCEL);
            nullAlert.setContentText("Please review information that you have entered /n You must have to enter isbn number ");
            nullAlert.initModality(Modality.WINDOW_MODAL);
            nullAlert.showAndWait();
            return;
        }
        newbook.setTitle(title.getText());
        newbook.setSubtitle(subtitle.getText());
        newbook.setIsbn(isbn.getText());
        newbook.setAuthors(authors.getText());
        newbook.setTranslators(translators.getText());
        newbook.setPublisher(publisher.getText());
        newbook.setDate(String.valueOf(date.getValue()));
        newbook.setEdition(edition.getText());
        newbook.setTags(tag.getText());

        newbook.setRating(bookRating.getRating());
        newbook.setLanguage(language.getText());
        addImage();
        newbook.setCover(imagePath);


        tempResults.add(newbook);
        observableBookList.add(newbook);
        saveBookInfoToJson(newbook);

        ResetInput();

    }
    public static void saveBookInfoToJson(Book book) {
        JsonObject bookJson = new JsonObject();
        bookJson.addProperty("title", book.getTitle());
        bookJson.addProperty("subtitle", book.getSubtitle());
        bookJson.addProperty("isbn", book.getIsbn());

        JsonArray authorsArray = new JsonArray();
        for (String author : book.getAuthors().split(",")) {
            authorsArray.add(author);
        }
        bookJson.add("authors", authorsArray);

        //
        JsonArray translatorsArray = new JsonArray();
        for (String translator : book.getTranslators().split(",")) {
            translatorsArray.add(translator);
        }
        bookJson.add("translators", translatorsArray);

        JsonArray tagsArray = new JsonArray();
        for (String tag : book.getTags().split(",")) {
            tagsArray.add(tag);
        }
        bookJson.add("tags", tagsArray);

        bookJson.addProperty("publisher", book.getPublisher());
        bookJson.addProperty("date", book.getDate());
        bookJson.addProperty("edition", book.getEdition());
        bookJson.addProperty("cover", book.getCover());
        bookJson.addProperty("rating", book.getRating());
        bookJson.addProperty("language", book.getLanguage());



        String folderPath = "Mylibrary/books/";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String baseFileName = book.getIsbn();
        String filePath = folderPath + File.separator + baseFileName + ".json";
        File file = new File(filePath);

        int count = 1;
        while (file.exists()) {
            String uniqueFileName = baseFileName + "-" + count;
            filePath = folderPath + File.separator + uniqueFileName + ".json";
            file = new File(filePath);
            count++;
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(bookJson);
            fileWriter.write(jsonString);
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ResetInput(){
        title.clear();
        subtitle.clear();
        isbn.clear();
        authors.clear();
        translators.clear();
        publisher.clear();
        edition.clear();
        tag.clear();
        date.setValue(null);
        language.clear();
        bookRating.setRating(0);
    }
    public boolean checkNull(){
        if(title.getText().isEmpty() && subtitle.getText().isEmpty() && isbn.getText().isEmpty() &&
                authors.getText().isEmpty() && translators.getText().isEmpty() && publisher.getText().isEmpty() && edition.getText().isEmpty() &&
                tag.getText().isEmpty() && language.getText().isEmpty() && bookRating.getRating() == 0 && language.getText().isEmpty() ){
            return true;
        } if(isbn.getText().isEmpty()){
            return true;
        }
        return false;
    }

    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}