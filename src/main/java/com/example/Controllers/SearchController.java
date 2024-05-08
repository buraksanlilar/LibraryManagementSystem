package com.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.Rating;

import java.util.ArrayList;

import static com.example.Controllers.MainController.observableBookList;
import static com.example.Controllers.MainController.tempResults;

public class SearchController {
    public static boolean searched = false;
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
    private TextField edition;
    @FXML
    private TextField tags;
    @FXML
    private Rating rating;
    @FXML
    private TextField language;
    @FXML
    private Button ResetButton;
    @FXML
    public ArrayList<Book> searchBooks() {

        String titleSearch = title.getText().toLowerCase();
        String subtitleSearch = subtitle.getText().toLowerCase();
        String isbnSearch = isbn.getText();
        String authorsSearch = authors.getText().toLowerCase();
        String translatorSearch = translator.getText().toLowerCase();
        String publisherSearch = publisher.getText().toLowerCase();
        String dateSearch = String.valueOf(date.getValue());
        String editionSearch = edition.getText();
        String tagSearch = tags.getText().toLowerCase();
        double ratingSearch = rating.getRating();
        String languageSearch = language.getText().toLowerCase();

        ArrayList<Book> results = new ArrayList<>();
        for (Book book : tempResults) {

            if (date.getValue() == null) {
                if ((titleSearch.isEmpty() || book.getTitle().toLowerCase().contains(titleSearch)) &&
                        (subtitleSearch.isEmpty() || book.getSubtitle().toLowerCase().contains(subtitleSearch)) &&
                        (isbnSearch.isEmpty() || book.getIsbn().contains(isbnSearch)) &&
                        (authorsSearch.isEmpty() || book.getAuthors().toLowerCase().contains(authorsSearch)) &&
                        (translatorSearch.isEmpty() || book.getTranslators().toLowerCase().contains(translatorSearch)) &&
                        (publisherSearch.isEmpty() || book.getPublisher().toLowerCase().contains(publisherSearch)) &&
                        (editionSearch.isEmpty() || book.getEdition().toLowerCase().contains(editionSearch)) &&
                        (tagSearch.isEmpty() || book.getTags().toLowerCase().contains(tagSearch)) &&
                        (languageSearch.isEmpty() || book.getLanguage().toLowerCase().contains(languageSearch)) && (ratingSearch == 0 || book.getRating() == ratingSearch)) {
                    results.add(book);
                }
            }
          else  {
                if (
                        (titleSearch.isEmpty() || book.getTitle().toLowerCase().contains(titleSearch)) &&
                                (subtitleSearch.isEmpty() || book.getSubtitle().toLowerCase().contains(subtitleSearch)) &&
                                (isbnSearch.isEmpty() || book.getIsbn().contains(isbnSearch)) &&
                                (authorsSearch.isEmpty() || book.getAuthors().toLowerCase().contains(authorsSearch)) &&
                                (translatorSearch.isEmpty() || book.getTranslators().toLowerCase().contains(translatorSearch)) &&
                                (publisherSearch.isEmpty() || book.getPublisher().toLowerCase().contains(publisherSearch)) &&
                                (editionSearch.isEmpty() || book.getEdition().toLowerCase().contains(editionSearch)) &&
                                (tagSearch.isEmpty() || book.getTags().toLowerCase().contains(tagSearch)) && (dateSearch.isEmpty() || book.getDate().contains(dateSearch)) && (languageSearch.isEmpty() || book.getLanguage().toLowerCase().contains(languageSearch))&&
                                (ratingSearch == 0 || book.getRating() == ratingSearch) ) {
                    results.add(book);
                }
            }
        }
        reset();
        searched = true;
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
        edition.clear();
        tags.clear();
        rating.setRating(0);
    }
    @FXML
    public void ResetButton() {
        observableBookList.clear();
        observableBookList.addAll(tempResults);
        reset();
    }

    @FXML
    public void Show() {
        ArrayList<Book> searchResults = searchBooks();
        observableBookList.setAll(searchResults);
    }
}