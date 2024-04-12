package com.example.hellofx1;

import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import static com.example.hellofx1.MainController.observableBookList;

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
    private TextField tags;

    private String imagePath;
    @FXML
    private Button resetButton;
    @FXML
    private Button addButton;
    @FXML
    private Button imageButton;

    @FXML
    public void addImage(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a picture");

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image files","*.png","*.jpg");
        Stage stage = (Stage) imageButton.getScene().getWindow();
        File file = fc.showOpenDialog(stage);

        if(file!=null){
            imagePath = file.getAbsolutePath();
        }
    }
    public void saveBookInfoToJson(String title, String subtitle, String isbn, String authors, String translators, String publisher, String date,
                                   String covertype, String edition, int page,String tags) {
        JsonObject bookJson = new JsonObject();
        bookJson.addProperty("title", title);
        bookJson.addProperty("subtitle", subtitle);
        bookJson.addProperty("isbn", isbn);
        bookJson.addProperty("authors", authors);
        bookJson.addProperty("translators", translators);
        bookJson.addProperty("publisher", publisher);
        bookJson.addProperty("date", date);
        bookJson.addProperty("covertype", covertype);
        bookJson.addProperty("edition", edition);
        bookJson.addProperty("page", page);

        bookJson.addProperty("tags", tags);


        String folderPath = "books";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String baseFileName = title;
        String filePath = folderPath + File.separator + baseFileName + ".json";
        File file = new File(filePath);

        int count = 1;
        while (file.exists()) {
            // aynı ad varsa arttırıor.
            String uniqueFileName = baseFileName + "-" + count;
            filePath = folderPath + File.separator + uniqueFileName + ".json";
            file = new File(filePath);
            count++;
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(bookJson);
            fileWriter.write(jsonString);
            System.out.println("JSON OLUSTU.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void AddNewBook(){
        Book newbook = new Book();
        newbook.setTitle(title.getText());
        newbook.setSubtitle(subtitle.getText());
        newbook.setIsbn(isbn.getText());
        newbook.setPublisher(publisher.getText());
        newbook.setAuthors(authors.getText());
        newbook.setTranslators(translators.getText());
        newbook.setTags(tags.getText());
        if(date.getValue() != null){
            newbook.setDate(date.getValue().toString());
        }
        newbook.setCovertype(covertype.getText());
        newbook.setEdition(edition.getText());
        if(!page.getText().isEmpty()){
            newbook.setPage(Integer.parseInt(page.getText()));
        }

       newbook.setCoverImage(imagePath);
        observableBookList.add(newbook);
        saveBookInfoToJson(title.getText(), subtitle.getText(), isbn.getText(), authors.getText(), translators.getText(), publisher.getText(), date.getValue().toString(), covertype.getText(), edition.getText(), Integer.parseInt(page.getText()), tags.getText());
        ResetInput();

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
        tags.clear();
        covertype.clear();
        date.setValue(null);
    }
}
