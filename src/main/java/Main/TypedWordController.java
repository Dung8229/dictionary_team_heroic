package Main;

import General.Dictionary;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebView;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class TypedWordController extends Dictionary implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView webView;
    @FXML
    private CheckBox bookmarkCheckBox;
    @FXML
    private ComboBox<Double> speedBox;
    @FXML
    private Button USbutton;
    @FXML
    private Button UKbutton;

    private ObservableList<String> typedWordList = FXCollections.observableArrayList();

    public void bookmarkWord() {
        bookmarkWord(listView, bookmarkCheckBox);
    }

    public void initWordList() {
        typedWordList.clear();
        try {
            BufferedReader typingGameReader = new BufferedReader(new FileReader("src/main/resources/Media/Text file/Typing Game/TypingGameWords.txt"));
            String word = typingGameReader.readLine();
            while (word != null) {
                if (word.charAt(0) == '0') {
                    typedWordList.add(hiddenWordLengthOf(word.length() - 1));
                } else {
                    typedWordList.add(word.substring(1).toLowerCase());
                }
                word = typingGameReader.readLine();
            }
            initWordList(typedWordList, listView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSpeedBoxValue(speedBox);

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String currentText) {
                updateListView(currentText, typedWordList, listView);
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
                bookmarkCheckBox.setDisable(false);
                USbutton.setDisable(false);
                UKbutton.setDisable(false);

            }
        });
    }

    private String hiddenWordLengthOf(int l) {
        String s = "";
        for (int i = 0; i < l; i++) {
            s += "*";
        }
        return s;
    }
}
