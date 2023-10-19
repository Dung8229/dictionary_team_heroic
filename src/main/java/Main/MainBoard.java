package Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainBoard implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private Button translateButton;
    @FXML
    private AnchorPane mainBoard;
    @FXML
    private AnchorPane searchPane;
    @FXML
    private AnchorPane translatePane;
    public void setMainBoard(AnchorPane pane) {
        mainBoard.getChildren().setAll(pane);
    }
    @FXML
    public void OpenSearchPane() {
        System.out.println("Search Open");
        setMainBoard(searchPane);
    }
    @FXML
    public void OpenTranslatePane() {
        System.out.println("Translate Open");
        setMainBoard(translatePane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            searchPane = FXMLLoader.load(MainBoard.class.getResource("/fxml/SearchPane.fxml"));
            translatePane = FXMLLoader.load(MainBoard.class.getResource("/fxml/TranslatePane.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setMainBoard(searchPane);
    }
}