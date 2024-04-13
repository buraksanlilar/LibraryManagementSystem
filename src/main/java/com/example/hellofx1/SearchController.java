package com.example.hellofx1;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchController {
    @FXML
    private TextField title;
    @FXML
    private TextField subtitle;
    @FXML
    private TextField isbn;
    @FXML
    private TextField authors;
    @FXML
    private TextField translator;
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
    public ArrayList<Book> searchBooks() {

        String titleSearch = title.getText().toLowerCase();
        String subtitleSearch = subtitle.getText().toLowerCase();
        String isbnSearch = isbn.getText();
        String authorsSearch = authors.getText().toLowerCase();
        String translatorSearch = translator.getText().toLowerCase();
        String publisherSearch = publisher.getText().toLowerCase();
        String dateSearch = String.valueOf(date.getValue());
        String coverTypeSearch = covertype.getText().toLowerCase();
        String editionSearch = edition.getText();
        String pageSearch = page.getText();
        String tagSearch = tag.getText().toLowerCase();

        ArrayList<Book> results = new ArrayList<>();
        for (Book book : MainController.tempResults) {

            //  MainController.observableBookList.setAll(MainController.tempResults);
            if (date.getValue() == null) {
                if ((titleSearch.isEmpty() || book.getTitle().toLowerCase().contains(titleSearch)) &&
                        (subtitleSearch.isEmpty() || book.getSubtitle().toLowerCase().contains(subtitleSearch)) &&
                        (isbnSearch.isEmpty() || book.getIsbn().equals(isbnSearch)) &&
                        (authorsSearch.isEmpty() || book.getAuthors().contains(authorsSearch)) &&
                        (translatorSearch.isEmpty() || book.getTranslators().contains(translatorSearch)) &&
                        (publisherSearch.isEmpty() || book.getPublisher().toLowerCase().contains(publisherSearch)) &&
                        (coverTypeSearch.isEmpty() || book.getCovertype().toLowerCase().contains(coverTypeSearch)) &&
                        (editionSearch.isEmpty() || book.getEdition().equals(editionSearch)) &&
                        (pageSearch.isEmpty() || book.getPage() == Integer.parseInt(pageSearch)) &&
                        (tagSearch.isEmpty() || book.getTags().contains(tagSearch))) {
                    results.add(book);
                }
            }
            {
                if (
                        (titleSearch.isEmpty() || book.getTitle().toLowerCase().contains(titleSearch)) &&
                                (subtitleSearch.isEmpty() || book.getSubtitle().toLowerCase().contains(subtitleSearch)) &&
                                (isbnSearch.isEmpty() || book.getIsbn().equals(isbnSearch)) &&
                                (authorsSearch.isEmpty() || book.getAuthors().contains(authorsSearch)) &&
                                (translatorSearch.isEmpty() || book.getTranslators().contains(translatorSearch)) &&
                                (publisherSearch.isEmpty() || book.getPublisher().toLowerCase().contains(publisherSearch)) &&
                                (coverTypeSearch.isEmpty() || book.getCovertype().toLowerCase().contains(coverTypeSearch)) &&
                                (editionSearch.isEmpty() || book.getEdition().equals(editionSearch)) &&
                                (pageSearch.isEmpty() || book.getPage() == Integer.parseInt(pageSearch)) &&
                                (tagSearch.isEmpty() || book.getTags().contains(tagSearch)) && (dateSearch.isEmpty() || book.getDate().contains(dateSearch))) {
                    results.add(book);
                }
            }
        }
        reset();
        return results;
    }

    public void reset() {
        title.clear();
        subtitle.clear();
        isbn.clear();
        authors.clear();
        translator.clear();
        publisher.clear();
        date.setValue(null);
        covertype.clear();
        edition.clear();
        page.clear();
        tag.clear();
    }

    @FXML

    public void Show() {
        ArrayList<Book> searchResults = searchBooks();
        MainController.observableBookList.setAll(searchResults);
    }
}