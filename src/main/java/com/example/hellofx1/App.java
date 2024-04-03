package com.example.hellofx1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.*;
import java.nio.file.Files;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private void newFile(TextArea textArea)
    {
        textArea.clear();
    }
    private void openFile(Stage stage,TextArea textArea)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select file to open!");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt","*.txt"));
        File f = fc.showOpenDialog(stage);
       if(f != null)
       {
           try {
              String content = Files.readString(f.toPath());
              textArea.setText(content);
           }catch (IOException e){
               e.printStackTrace();
           }
       }
    }
    private void saveFile(Stage stage,TextArea textArea)
    {

        FileChooser fc = new FileChooser();
        fc.setTitle("Select file to save!");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
        File f = fc.showSaveDialog(stage);

        if(f != null)
        {
            try {
                Files.writeString(f.toPath(),textArea.getText());
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void helpDisplay()
    {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Information !!!");
       alert.setHeaderText("about 2024");
       alert.showAndWait();
    }

    @Override
    public void start(Stage stage) throws Exception {



        VBox mainLayout = new VBox();
        TextArea textArea = new TextArea();
        VBox.setVgrow(textArea,Priority.ALWAYS);


        MenuBar menuBar = new MenuBar();

        Menu mFile = new Menu("File");
        Menu mHelp = new Menu("Help");

        MenuItem myNewFile = new MenuItem("New");
        myNewFile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        myNewFile.setOnAction(e->newFile(textArea));

        MenuItem myOpen = new MenuItem("Open");
        myOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        myOpen.setOnAction( e-> openFile(stage,textArea));

        MenuItem mySave = new MenuItem("Save");
        mySave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        mySave.setOnAction(e->saveFile(stage,textArea));

       MenuItem About = new MenuItem("About");
       About.setOnAction(e->helpDisplay());

       mHelp.getItems().add(About);


        mFile.getItems().addAll(myNewFile,myOpen,mySave);

        menuBar.getMenus().addAll(mFile,mHelp);
        mainLayout.getChildren().addAll(menuBar,textArea);





        Scene scene = new Scene(mainLayout, 400,
                300);
        stage.setTitle("Book Catalog");
        stage.setScene(scene);
        stage.show();

    }
}