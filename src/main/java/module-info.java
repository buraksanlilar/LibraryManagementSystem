module com.example.hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.hellofx1 to javafx.fxml;
    exports com.example.hellofx1;
}