module com.example.examen_thewalkingdam {
    requires javafx.controls;
    requires javafx.fxml;
                requires kotlin.stdlib;
    requires java.sql;
    requires java.desktop;


    opens com.example.examen_thewalkingdam to javafx.fxml;
    exports com.example.examen_thewalkingdam;
}