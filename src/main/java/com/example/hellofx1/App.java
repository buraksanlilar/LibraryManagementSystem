package com.example.hellofx1;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;


public class App extends Application {

    public static void main(String[] args) {

        launch(args);


    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            AnchorPane root = fxmlLoader.load();
            stage.setTitle("FXMLLoader Example");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

            Book book = getBookInfoFromUser((AnchorPane) root);
            if (book != null) {
                writeBookInfoToJson(book);
            } else {
                System.out.println("Error: Failed to retrieve book information.");
            }
        } catch (IOException e) {
            System.out.println("Error loading FXML file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
        private Book getBookInfoFromUser(AnchorPane anchorPane) {
            // AnchorPane içindeki TextField ve DatePicker alanlarına eriş
            TextField titleField = (TextField) anchorPane.lookup("#Book Title");
            TextField subTitleField = (TextField) anchorPane.lookup("#Subtitle");
            TextField isbnField = (TextField) anchorPane.lookup("#ISBN");
            TextField authorsField = (TextField) anchorPane.lookup("#Authors");
            TextField translatorsField = (TextField) anchorPane.lookup("#Translators");
            TextField publisherField = (TextField) anchorPane.lookup("#Publisher");
            TextField dateField = (TextField) anchorPane.lookup("#Date");
            TextField editionField = (TextField) anchorPane.lookup("#Edition");
            TextField coverTypeBox = (TextField) anchorPane.lookup("#Cover Type");

            // Kullanıcıdan kitap bilgilerini al
            Book book = new Book();
            book.setTitle(titleField.getText());
            book.setSubTitle(subTitleField.getText());
            book.setIsbn(isbnField.getText());
            book.setAuthors(authorsField.getText());
            book.setTranslators(translatorsField.getText());
            book.setPublisher(publisherField.getText());
            book.setEdition(editionField.getText());
            book.setCoverType(coverTypeBox.getText());
            book.setDate(dateField.getText());

            return book;
        }



    private void writeBookInfoToJson(Book book) {
        Gson gson = new Gson();
        String json = gson.toJson(book);

        try (FileWriter writer = new FileWriter("book.json")) {
            writer.write(json);
            System.out.println("JSON dosyası başarıyla oluşturuldu.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}