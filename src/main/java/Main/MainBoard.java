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
    private Button bookmarkButton;
    @FXML
    private Button historyButton;

    @FXML
    private AnchorPane mainBoard;
    @FXML
    private AnchorPane searchPane;
    @FXML
    private AnchorPane translatePane;
    @FXML
    private AnchorPane bookmarkPane;
    @FXML
    private AnchorPane historyPane;

    @FXML
    private BookmarkController bookmarkController;
    @FXML
    private HistoryController historyController;

    public void setMainBoard(AnchorPane pane) {
        mainBoard.getChildren().setAll(pane);
    }

    public void OpenSearchPane() {
        setMainBoard(searchPane);
    }
    public void OpenTranslatePane() {
        setMainBoard(translatePane);
    }
    public void OpenHistoryPane() {
        setMainBoard(historyPane);
        historyController.initHistoryList();
    }
    public void OpenBookmarkPane() {
        setMainBoard(bookmarkPane);
        bookmarkController.initBookmarkedList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            searchPane = FXMLLoader.load(MainBoard.class.getResource("/fxml/SearchPane.fxml"));
            translatePane = FXMLLoader.load(MainBoard.class.getResource("/fxml/TranslatePane.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader loader = new FXMLLoader(MainBoard.class.getResource("/fxml/BookmarkPane.fxml"));
            bookmarkPane = loader.load();
            bookmarkController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader loader = new FXMLLoader(MainBoard.class.getResource("/fxml/HistoryPane.fxml"));
            historyPane = loader.load();
            historyController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setMainBoard(searchPane);
    }
}