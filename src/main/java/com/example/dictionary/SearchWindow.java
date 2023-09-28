package com.example.dictionary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SearchWindow {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}