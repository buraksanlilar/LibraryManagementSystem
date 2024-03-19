module com.example.hellofx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hellofx1 to javafx.fxml;
    exports com.example.hellofx1;
}