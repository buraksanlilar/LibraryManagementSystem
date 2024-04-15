module com.example.hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.controlsfx.controls;


    opens com.example.Controllers to javafx.fxml;
    exports com.example.Controllers;
}