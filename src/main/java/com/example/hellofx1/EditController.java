package com.example.hellofx1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

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
    private Button editButton;

    @FXML
    private Button resetButton;

    public void setBookToEdit(Book book) {
        this.bookToEdit = book;

        // Kitap bilgilerini ekranda göster
        title.setText(book.getTitle());
        subtitle.setText(book.getSubtitle());
        isbn.setText(book.getIsbn());
        authors.setText(String.valueOf(book.getAuthors()));
        translators.setText(String.valueOf(book.getTranslators()));
        publisher.setText(book.getPublisher());
        if (book.getDate() != null) {
            date.setValue(LocalDate.parse(book.getDate()));

        }
        covertype.setText(book.getCovertype());
        edition.setText(book.getEdition());
        page.setText(String.valueOf(book.getPage()));
        tags.setText(String.valueOf(book.getTags()));
    }
    public void saveBookInfoToJson(String title, String subtitle, String isbn, String authors, String translators, String publisher, String date,
                                   String covertype, String edition, int page, String tags) {
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
        String filePath = folderPath + File.separator + title + ".json";
        try (FileWriter file = new FileWriter(filePath)) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(bookJson);
            file.write(jsonString);
            System.out.println("JSON OLUSTU.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void editBook() {
        // Kitap bilgilerini güncelle
        bookToEdit.setTitle(title.getText());
        bookToEdit.setSubtitle(subtitle.getText());
        bookToEdit.setIsbn(isbn.getText());
        bookToEdit.setAuthors(authors.getText());
        bookToEdit.setTranslators(translators.getText());
        bookToEdit.setPublisher(publisher.getText());
        bookToEdit.setDate(date.getValue().toString());
        bookToEdit.setCovertype(covertype.getText());
        bookToEdit.setEdition(edition.getText());
        bookToEdit.setPage(Integer.parseInt(page.getText()));
        bookToEdit.setTags(tags.getText());

        saveBookInfoToJson(title.getText(), subtitle.getText(), isbn.getText(), authors.getText(), translators.getText(), publisher.getText(), date.getValue().toString(), covertype.getText(), edition.getText(), Integer.parseInt(page.getText()), tags.getText());

        System.out.println("Kitap güncellendi.");
    }

    @FXML
    void resetInput() {
        // Ekrandaki bilgileri temizle
        title.clear();
        subtitle.clear();
        isbn.clear();
        authors.clear();
        translators.clear();
        publisher.clear();
        date.setValue(null);
        covertype.clear();
        edition.clear();
        page.clear();
        tags.clear();
    }
}