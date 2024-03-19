package com.example.hellofx1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private Label label;
    private TextField txtInfo;
    private ListView<String> listView;
    private Button bttnAdd, bttnOK;
    private void addAction() {
        if(txtInfo.getText().isBlank()) return;
        listView.getItems().add(txtInfo.getText());
        txtInfo.clear();
    }

//denens
    @Override
    public void start(Stage stage) throws Exception {
        VBox mainLayout = new VBox();

        HBox firstLine = new HBox(8); // widgetların arasındaki mesafeyi ayarlıyor
        firstLine.setAlignment(Pos.CENTER);
        label = new Label("Book name: ");
        txtInfo = new TextField();
        HBox.setHgrow(txtInfo, Priority.ALWAYS);
        bttnAdd = new Button("Add");
        bttnAdd.setOnAction(e -> addAction());

        listView = new ListView<String>();
        VBox.setVgrow(listView, Priority.ALWAYS);

        HBox lastLine = new HBox(8);
        lastLine.setAlignment(Pos.CENTER_RIGHT);
        bttnOK = new Button("OK");
        bttnOK.setOnAction(e -> stage.close());
        lastLine.getChildren().add(bttnOK);

        VBox.setMargin(firstLine, new Insets(8));
        VBox.setMargin(listView, new Insets(8));
        VBox.setMargin(lastLine, new Insets(8));
        // dış duvarla arasındaki mesafeyi ayarlıyor

        firstLine.getChildren().addAll(
                label, txtInfo, bttnAdd
        );

        mainLayout.getChildren().addAll(firstLine,
                listView, lastLine);

        Scene scene = new Scene(mainLayout, 400,
                300);

        stage.setTitle("Book Catalog");
        stage.setScene(scene);
        stage.show();

    }
}