package com.example.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private TextField covertype;
    @FXML
    private TextField edition;
    @FXML
    private TextField page;
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
    private String imagePath;
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
        fileChooser.setTitle("Resim Seç");


        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Select a image", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);


        Stage stage = (Stage) imageButton.getScene().getWindow();
        imageFile = fileChooser.showOpenDialog(stage);


        if (imageFile != null) {
            Image image = new Image(imageFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void addImage(){
        if(imageFile != null){
            imagePath = imageFile.getAbsolutePath();
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

        newbook.setCovertype(covertype.getText());
        newbook.setEdition(edition.getText());
        try {
            if(!page.getText().isEmpty()){
                newbook.setPage(Integer.parseInt(page.getText()));
            }
        }catch (Exception E){
            Alert deleteAlert = new Alert(Alert.AlertType.ERROR,"Back", ButtonType.OK,ButtonType.CLOSE);
            deleteAlert.setContentText("Invalid page type");
            deleteAlert.initModality(Modality.APPLICATION_MODAL);
            deleteAlert.showAndWait();
            page.clear();
            return;
        }

        newbook.setTags(tag.getText());

        newbook.setRating(bookRating.getRating());
        newbook.setLanguage(language.getText());
        addImage();
        newbook.setCoverImage(imagePath);


        tempResults.add(newbook);
        observableBookList.add(newbook);
        saveBookInfoToJson(newbook);

        ResetInput();

    }
    public void saveBookInfoToJson(Book book) {
        JsonObject bookJson = new JsonObject();
        bookJson.addProperty("title", book.getTitle());
        bookJson.addProperty("subtitle", book.getSubtitle());
        bookJson.addProperty("isbn", book.getIsbn());
        bookJson.addProperty("authors", book.getAuthors());
        bookJson.addProperty("translators", book.getTranslators());
        bookJson.addProperty("publisher", book.getPublisher());
        bookJson.addProperty("date", book.getDate());
        bookJson.addProperty("covertype", book.getCovertype());
        bookJson.addProperty("edition", book.getEdition());
        bookJson.addProperty("page", book.getPage());
        bookJson.addProperty("tag", book.getTags());
        bookJson.addProperty("coverImage", book.getCoverImage());
        bookJson.addProperty("rating", book.getRating());
        bookJson.addProperty("language", book.getLanguage());


        String folderPath = temporaryFolder;
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
        page.clear();
        tag.clear();
        covertype.clear();
        date.setValue(null);
        language.clear();
        bookRating.setRating(0);
    }
    public boolean checkNull(){
        if(title.getText().isEmpty() && subtitle.getText().isEmpty() && isbn.getText().isEmpty() &&
                authors.getText().isEmpty() && translators.getText().isEmpty() && publisher.getText().isEmpty() && edition.getText().isEmpty() &&
                page.getText().isEmpty() && tag.getText().isEmpty() && covertype.getText().isEmpty() && language.getText().isEmpty() && bookRating.getRating() == 0 && language.getText().isEmpty() ){
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