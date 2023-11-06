package Main;

import General.Dictionary;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class BookmarkController extends Dictionary implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView webView;
    @FXML
    private CheckBox bookmarkCheckBox;

    public void bookmarkWord() {
        bookmarkWord(listView, bookmarkCheckBox);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initWordList(bookmarkedList, listView);
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String currentText) {
                updateListView(currentText, bookmarkedList, listView);
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

                String word = listView.getSelectionModel().getSelectedItem();
                ThreadCreateAudioFileUS = new Thread(new TaskCreateAudioFileUS(word));
                ThreadCreateAudioFileUK = new Thread(new TaskCreateAudioFileUK(word));
                try {
                    ThreadCreateAudioFileUS.start();
                    ThreadCreateAudioFileUK.start();
                    webView.getEngine().loadContent(getDetail(word), "text/html");
                    addHistoryList(word);
                    handleBookmarkCheckBoxSelected(bookmarkCheckBox, word);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
