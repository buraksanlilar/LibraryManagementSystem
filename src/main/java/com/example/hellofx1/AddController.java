package com.example.hellofx1;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.example.hellofx1.Library.books;

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
    private TextField date;
    @FXML
    private TextField covertype;
    @FXML
    private TextField edition;
    @FXML
    private TextField page;
    @FXML
    private TextField image;
    @FXML
    private TextField tag;

    @FXML
    public void AddNewBook(){
        Book newbook = new Book();

        newbook.setTitle(title.getText());
        newbook.setSubTitle(subtitle.getText());
        newbook.setIsbn(isbn.getText());
        newbook.setAuthors(authors.getText());
        newbook.setTranslators(translators.getText());
        newbook.setPublisher(publisher.getText());
        newbook.setDate(date.getText());
        newbook.setCoverType(covertype.getText());
        newbook.setEdition(edition.getText());
        newbook.setPage(Integer.parseInt(page.getText()));
        newbook.setCoverImage(image.getText());
        newbook.setTags(tag.getText());

        books.add(newbook);

    }
    @FXML
    public void ResetInput(){
        title.clear();
        subtitle.clear();
        isbn.clear();
        authors.clear();
        translators.clear();

    }
}
