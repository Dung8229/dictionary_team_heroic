package Main;

import General.Dictionary;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class BookmarkController extends Dictionary {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> wordList;
    @FXML
    private WebView webView;

    public void initBookmarkedList() {
        wordList.setItems(bookmarkedList);
    }
}
