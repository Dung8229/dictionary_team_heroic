module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;


    opens Code to javafx.fxml;
    exports Code;
}