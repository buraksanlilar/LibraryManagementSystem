package com.example.hellofx1;

import java.util.ArrayList;
import java.util.Arrays;

public class Book {
    private String title;
    private String subtitle;
    private ArrayList<String> authors;
    private ArrayList<String>translators;
    private String isbn;
    private String publisher;
    private String date;
    private String coverType;
    private String edition;
    private int page;
    private String coverImage;
    private ArrayList<String> tags;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title==null) this.title = "unknown";
        this.title = title;
    }

    public String getSubTitle() {
        return subtitle;
    }

    public void setSubTitle(String subtitle) {
        if(subtitle==null){
            this.subtitle = "unknown";
        }
        this.subtitle = subtitle;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        String newString = authors.replaceAll("\\s+","");
        String[] stringArr = authors.split(",");
        this.authors = new ArrayList<>(Arrays.asList(stringArr));
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public void setTranslators(String translators) {
        String newString = translators.replaceAll("\\s+","");
        String[] stringArr = translators.split(",");
        this.translators = new ArrayList<>(Arrays.asList(stringArr));
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoverType() {
        return coverType;
    }

    public void setCoverType(String coverType) {
        this.coverType = coverType;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(String tags) {
        String newString = tags.replaceAll("\\s+","");
        String[] stringArr = tags.split(",");
        this.tags = new ArrayList<>(Arrays.asList(stringArr));
    }
    public Book(){

    }

}
