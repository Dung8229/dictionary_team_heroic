package code.dictionary;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @FXML
    private Button SearchButton;
    @FXML
    private Button GameButton;
    @FXML
    private AnchorPane MainPane;
    @FXML
    private AnchorPane SearchPane;
    @FXML
    private AnchorPane GamePane;

    public void showSearchPane() {}
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}