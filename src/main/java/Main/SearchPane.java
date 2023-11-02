package Main;

import General.DatabaseConnection;
import General.Dictionary;
import Task.TaskCreateAudioFile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
import com.voicerss.tts.VoiceProvider;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebView;

public class SearchPane extends Dictionary implements Initializable {
    private static final String API_KEY = "d3bf64e482e14c818dcfb90f0f861ecd";
    private static final File voiceUS_WAV = new File("src/main/resources/Media/Audio/VoiceUS.wav");
    private static final File voiceUK_WAV = new File("src/main/resources/Media/Audio/VoiceUK.wav");
    private static final VoiceProvider tts = new VoiceProvider(API_KEY);
    private static final String GET_WORD_QUERY = "SELECT * FROM tbl_edict";
    private static final String GET_WORD_DETAIL_QUERY = "SELECT * FROM tbl_edict WHERE word = ";
    private static final String GET_WORD_LIKE_PATTERN_QUERY = "SELECT word FROM tbl_edict WHERE word LIKE ";

    @FXML
    private Button USbutton;
    @FXML
    private Button UKbutton;
    @FXML
    private CheckBox bookmarkCheckBox;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView webView;

    public Thread threadCreateAudioFile;
    private static VoiceParameters para;
    private DatabaseConnection dbConnection;
    private Connection connection;
    private Statement statement;

    public static void createAudioFile(String word) {
        para.setText(word);

        try {
            para.setLanguage(Languages.English_UnitedStates);
            byte[] voice = tts.speech(para);

            FileOutputStream fos = new FileOutputStream(voiceUS_WAV);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            para.setLanguage(Languages.English_GreatBritain);
            byte[] voice = tts.speech(para);

            FileOutputStream fos = new FileOutputStream(voiceUK_WAV);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();    
        }
    }

    public void speakUSvoice() {
        try {
            threadCreateAudioFile.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(voiceUS_WAV.toURI().toString()));
        mediaPlayer.play();
    }

    public void speakUKvoice() {
        try {
            threadCreateAudioFile.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(voiceUK_WAV.toURI().toString()));
        mediaPlayer.play();
    }

    public void bookmarkWord() {
        String word = listView.getSelectionModel().getSelectedItem();
        if (word != null) {
            int index = dictionaryList.indexOf(word);
            if (bookmarkCheckBox.isSelected() && isBookmarkedList.get(index) == false) {
                isBookmarkedList.set(index, true);
                bookmarkedList.add(0, word);
            } else {
                isBookmarkedList.set(index, false);
                bookmarkedList.remove(word);
            }
        }
    }

    public void handleBookmarkCheckBoxSelected(String word) {
        if (isBookmarkedList.get(dictionaryList.indexOf(word)) == false) {
            bookmarkCheckBox.setSelected(false);
        } else {
            bookmarkCheckBox.setSelected(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        para = new VoiceParameters("Welcome", Languages.English_UnitedStates);
        para.setCodec(AudioCodec.WAV);
        para.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        para.setBase64(false);
        para.setSSML(false);
        para.setRate(0);

        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        listView.setItems(dictionaryList);

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
                threadCreateAudioFile = new Thread(new TaskCreateAudioFile(word));
                try {
                    threadCreateAudioFile.start();
                    webView.getEngine().loadContent(getDetail(word), "text/html");
                    historyList.add(0, word);
                    handleBookmarkCheckBoxSelected(word);
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