module code.dictionary {
    requires javafx.controls;
    requires javafx.fxml;


    opens code.dictionary to javafx.fxml;
    exports code.dictionary;
}