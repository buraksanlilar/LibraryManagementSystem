package com.example.hellofx1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

public class EditController {

    private Label editButton;
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
    private Button addButton;

    @FXML
    private Button resetButton;

    @FXML
    void AddNewBook(ActionEvent event) {

        System.out.println("Kitap eklendi.");
    }

    @FXML
    void ResetInput(ActionEvent event) {

        title.setText("");
        subtitle.setText("");
        isbn.setText("");
        authors.setText("");
        translators.setText("");
        publisher.setText("");
        date.setValue(null);
        covertype.setText("");
        edition.setText("");
        page.setText("");
        tag.setText("");
    }
}
