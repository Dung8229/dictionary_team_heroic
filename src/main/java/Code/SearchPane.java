package Code;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SearchPane implements Initializable {
    private static final String API_KEY = "d3bf64e482e14c818dcfb90f0f861ecd";
    private static final File voiceWAV = new File("src/main/resources/Media/voice.wav");
    private static final VoiceProvider tts = new VoiceProvider(API_KEY);
    private VoiceParameters para;

    @FXML
    private Button USbutton;
    @FXML
    private Button UKbutton;
    @FXML
    private TextField searchField;
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
    }
}