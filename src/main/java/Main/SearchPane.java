package Main;

import General.DatabaseConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebView;

public class SearchPane implements Initializable {
    private static final String API_KEY = "d3bf64e482e14c818dcfb90f0f861ecd";
    private static final File voiceWAV = new File("src/main/resources/Media/Audio/voice.wav");
    private static final VoiceProvider tts = new VoiceProvider(API_KEY);
    private VoiceParameters para;

    @FXML
    private Button USbutton;
    @FXML
    private Button UKbutton;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> wordList;
    @FXML
    private WebView webView;

    private static final String GET_WORD_QUERY = "SELECT * FROM tbl_edict";
    private static final String GET_WORD_DETAIL_QUERY = "SELECT * FROM tbl_edict WHERE word = ";
    public Set<String> words = new TreeSet<>();

    public void speakUSvoice() {
        para.setLanguage(Languages.English_UnitedStates);
        speakVoice(searchField.getText());
    }

    public void speakUKvoice() {
        para.setLanguage(Languages.English_GreatBritain);
        speakVoice(searchField.getText());
    }
    
    private void speakVoice(String word) {
        try {
            para.setText(word);

            byte[] voice = tts.speech(para);

            FileOutputStream fos = new FileOutputStream(voiceWAV);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

            MediaPlayer mediaPlayer = new MediaPlayer(new Media(voiceWAV.toURI().toString()));
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
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

        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(GET_WORD_QUERY);
            while (queryOutput.next()) {
                wordList.getItems().add(queryOutput.getString("word"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        wordList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

                String word = wordList.getSelectionModel().getSelectedItem();
                try {
                    Statement statement = connection.createStatement();
                    ResultSet queryOutput = statement.executeQuery(GET_WORD_DETAIL_QUERY + "\"" + word + "\"");
                    while (queryOutput.next()) {
                        webView.getEngine().loadContent(queryOutput.getString("detail"), "text/html");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}