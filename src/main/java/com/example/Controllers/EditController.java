package com.example.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import static com.example.Controllers.MainController.observableBookList;
import static com.example.Controllers.MainController.tempResults;

public class EditController {

    private Book bookToEdit;

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
    @FXML
    private TextField language;
    @FXML
    private Rating rating;
    @FXML
    private ImageView imageView;
    @FXML
    private Button imageButton;
    @FXML
    private Button resetButton;
    boolean checkDateNull = false;
    private File imageFile;

    private String tempIsbn;
    @FXML
    public void editImage(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");


        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);


        Stage stage = (Stage) imageButton.getScene().getWindow();
        imageFile = fileChooser.showOpenDialog(stage);


        if (imageFile != null) {
            Image image = new Image(imageFile.toURI().toString());
            imageView.setImage(image);
        }
    }
    public void addImage() {
        String imagePath = bookToEdit.getCoverImage();

        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    System.out.println(imagePath + " succesfully deleted.");
                }
            }
        }

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
                if (imageFile != null) {
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
                    bookToEdit.setCoverImage(targetPath);
                }

    }

    public void showInformation(Book editbook) {
        bookToEdit = editbook;

        String imagePath = editbook.getCoverImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(new File(imagePath).toURI().toString());
            imageView.setImage(image);
        }
        if (bookToEdit.getTitle() != null) {

            title.setText(editbook.getTitle());
        }
        if (bookToEdit.getSubtitle() != null) {

            subtitle.setText(editbook.getSubtitle());
        }
        if (bookToEdit.isbn != null) {

            isbn.setText(editbook.getIsbn());
        }
        authors.setText(editbook.getAuthors());
        translators.setText(editbook.getTranslators());
        publisher.setText(editbook.getPublisher());
        language.setText(editbook.getLanguage());
        if (editbook.getDate() != null && !editbook.getDate().equalsIgnoreCase("null") && !editbook.getDate().isEmpty()) {
            date.setValue(LocalDate.parse(editbook.getDate()));
            checkDateNull = false;
        } else {
            date.setValue(null);
            checkDateNull = true;
        }
        covertype.setText(editbook.getCovertype());
        edition.setText(editbook.getEdition());
        page.setText(String.valueOf(editbook.getPage()));
        rating.setRating(editbook.getRating());
        tags.setText(editbook.getTags());
    }

    @FXML
    public void confirmChanges() {

        tempIsbn = bookToEdit.getIsbn();
        bookToEdit.setTitle(title.getText());
        bookToEdit.setSubtitle(subtitle.getText());

        bookToEdit.setIsbn(isbn.getText());
        bookToEdit.setAuthors(authors.getText());
        bookToEdit.setTranslators(translators.getText());
        bookToEdit.setPublisher(publisher.getText());
        if (date.getValue() != null) {
            bookToEdit.setDate(date.getValue().toString());
        }
        bookToEdit.setCovertype(covertype.getText());
        bookToEdit.setEdition(edition.getText());
        bookToEdit.setPage(Integer.parseInt(page.getText()));
        bookToEdit.setTags(tags.getText());
        bookToEdit.setRating(rating.getRating());
        bookToEdit.setLanguage(language.getText());

        addImage();


        updateJsonFile(bookToEdit);


        int index = tempResults.indexOf(bookToEdit);
        if (index != -1) {
            tempResults.set(index, bookToEdit);
        }
        index = observableBookList.indexOf(bookToEdit);
        if (index != -1) {
            observableBookList.set(index, bookToEdit);
        }

        System.out.println("Book is updated");


    }



    private void updateJsonFile(Book bookToEdit) {
        String folderPath = "MyLibrary/books/";
        String oldFilePath = folderPath + File.separator + tempIsbn + ".json";
        String newFilePath = folderPath + File.separator + bookToEdit.getIsbn() + ".json";
        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);

        if (!tempIsbn.equals(bookToEdit.getIsbn())) {
            if (oldFile.exists()) {
                if (oldFile.delete()) {
                    System.out.println("Old file deleted");
                } else {
                    System.out.println("Old file cannot deleted.");
                }
            } else {
                System.out.println("Cannot find old file: " + oldFilePath);
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject bookJson = new JsonObject();


        bookJson.addProperty("title", bookToEdit.getTitle());
        bookJson.addProperty("subtitle", bookToEdit.getSubtitle());
        bookJson.addProperty("isbn", bookToEdit.getIsbn());
        bookJson.addProperty("authors", bookToEdit.getAuthors());
        bookJson.addProperty("translators", bookToEdit.getTranslators());
        bookJson.addProperty("publisher", bookToEdit.getPublisher());
        bookJson.addProperty("date", bookToEdit.getDate());
        bookJson.addProperty("cover", bookToEdit.getCovertype());
        bookJson.addProperty("edition", bookToEdit.getEdition());
        bookJson.addProperty("page", bookToEdit.getPage());
        bookJson.addProperty("tag", bookToEdit.getTags());
        bookJson.addProperty("rating", bookToEdit.getRating());
        bookJson.addProperty("language", bookToEdit.getLanguage());
        bookJson.addProperty("coverImage", bookToEdit.getCoverImage());


        try (FileWriter fileWriter = new FileWriter(newFile)) {
            String jsonString = gson.toJson(bookJson);
            fileWriter.write(jsonString);
            System.out.println("New JSON File created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ResetInput() {
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
        language.clear();
        rating.setRating(0);
    }

}