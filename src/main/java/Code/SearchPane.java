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
    private VoiceParameters USpara;
    private VoiceParameters UKpara;

    @FXML
    private Button USbutton;
    @FXML
    private Button UKbutton;
    @FXML
    private TextField searchField;
    public void speakUSvoice() {
        try {
            USpara.setText(searchField.getText());

            byte[] voice = tts.speech(USpara);

            FileOutputStream fos = new FileOutputStream(voiceWAV);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

            Media voiceWAVmedia = new Media(voiceWAV.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(voiceWAVmedia);
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void speakUKvoice() {
        try {
            UKpara.setText(searchField.getText());

            byte[] voice = tts.speech(UKpara);

            FileOutputStream fos = new FileOutputStream(voiceWAV);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

            Media voiceWAVmedia = new Media(voiceWAV.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(voiceWAVmedia);
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        USpara = new VoiceParameters("Welcome", Languages.English_UnitedStates);
        USpara.setCodec(AudioCodec.WAV);
        USpara.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        USpara.setBase64(false);
        USpara.setSSML(false);
        USpara.setRate(0);

        UKpara = new VoiceParameters("Welcome", Languages.English_GreatBritain);
        UKpara.setCodec(AudioCodec.WAV);
        UKpara.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        UKpara.setBase64(false);
        UKpara.setSSML(false);
        UKpara.setRate(0);
    }
}