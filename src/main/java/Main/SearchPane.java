package Main;

import General.DatabaseConnection;
import General.Dictionary;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.VoiceParameters;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class SearchPane extends Dictionary implements Initializable {
    @FXML
    private CheckBox bookmarkCheckBox;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView webView;
    @FXML
    private ComboBox<Double> speedBox;

    public void bookmarkWord() {
        bookmarkWord(listView, bookmarkCheckBox);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initWordList(dictionaryList, listView);
        initSpeedBoxValue(speedBox);

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String currentText) {
                updateListView(currentText, dictionaryList, listView);
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

//    private String Reformat(String word, String htmlText) {
//        htmlText = htmlText.replaceAll("<[Q|/Q]>", "");
//        Pattern pattern = Pattern.compile("@[^(<br />)]*[<br />]");
//        Matcher matcher = pattern.matcher(htmlText);
//        htmlText = matcher.replaceAll("@" + word + "<br />");
//        plainText = plainText.replaceAll("@[^(<br />)]*[<br />]", "hihi<br />");
//        return htmlText;
//    }
}