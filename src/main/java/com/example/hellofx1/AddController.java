package com.example.hellofx1;

import javafx.scene.control.TextField;

import static com.example.hellofx1.Library.books;

public class AddController {
    private TextField title;
    private TextField subtitle;
    private TextField isbn;
    private TextField authors;
    private TextField translators;
    private TextField publisher;
    private TextField date;
    private TextField covertype;
    private TextField edition;
    private TextField page;
    private TextField image;
    private TextField tag;

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
}
