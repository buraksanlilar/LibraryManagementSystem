package com.example.hellofx1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import org.controlsfx.control.Rating;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static com.example.hellofx1.MainController.observableBookList;
import static com.example.hellofx1.MainController.tempResults;

public class EditController {

    private Book bookToEdit; // Düzenlenmek istenen kitap

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
    private ImageView image;

    @FXML
    private Button editButton;

    @FXML
    private Button resetButton;
    boolean checkDateNull = false;
    String tempTitle;
    private String tempIsbn;


    public void showInformation(Book editbook) {
        bookToEdit = editbook;
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
        // Kitap bilgilerini güncelle
        tempTitle = bookToEdit.getTitle();

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

        // Mevcut JSON dosyasını güncelle
        updateJsonFile(bookToEdit);

        // İlgili listeleri güncelle
        int index = tempResults.indexOf(bookToEdit);
        if (index != -1) {
            tempResults.set(index, bookToEdit);
        }
        index = observableBookList.indexOf(bookToEdit);
        if (index != -1) {
            observableBookList.set(index, bookToEdit);
        }

        System.out.println("Kitap güncellendi.");


    }


    private void updateJsonFile(Book bookToEdit) {
        String folderPath = "MyLibrary/books/";
        String oldFilePath = folderPath + File.separator + tempIsbn + ".json";
        String newFilePath = folderPath + File.separator + bookToEdit.getIsbn() + ".json";
        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);

        if (!tempIsbn.equals(bookToEdit.getIsbn())) { // Eğer eski ISBN yeni ISBN'e eşit değilse
            if (oldFile.exists()) {
                if (oldFile.delete()) {
                    System.out.println("Eski dosya silindi.");
                } else {
                    System.out.println("Eski dosya silinemedi.");
                }
            } else {
                System.out.println("Eski dosya bulunamadı: " + oldFilePath);
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject bookJson = new JsonObject();

        // Kitap bilgilerini JSON nesnesine ekle
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

        // Yeni JSON dosyasını oluştur
        try (FileWriter fileWriter = new FileWriter(newFile)) {
            String jsonString = gson.toJson(bookJson);
            fileWriter.write(jsonString);
            System.out.println("Yeni JSON dosyası oluşturuldu.");
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
        // image.clear();
        tags.clear();
        covertype.clear();
        date.setValue(null);
        language.clear();
        rating.setRating(0);
    }
    public boolean checkNull(){
        if(title.getText().isEmpty() && subtitle.getText().isEmpty() && isbn.getText().isEmpty() &&
                authors.getText().isEmpty() && translators.getText().isEmpty() && publisher.getText().isEmpty() && edition.getText().isEmpty() &&
                page.getText().isEmpty() && tags.getText().isEmpty() && covertype.getText().isEmpty() && language.getText().isEmpty() && rating.getRating() == 0 && language.getText().isEmpty() ){
            return true;
        }
        return false;
    }

}