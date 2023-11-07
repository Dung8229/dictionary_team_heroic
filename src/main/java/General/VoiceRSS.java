package General;

import Main.MainBoard;
import Main.SearchPane;
import com.voicerss.tts.*;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class VoiceRSS {
    protected static final String API_KEY = "d3bf64e482e14c818dcfb90f0f861ecd";
    protected static final File voiceUS_WAV = new File("src/main/resources/Media/Audio/VoiceUS.wav");
    protected static final File voiceUK_WAV = new File("src/main/resources/Media/Audio/VoiceUK.wav");
    protected static final File voiceVN_WAV = new File("src/main/resources/Media/Audio/VoiceVN.wav");
    protected static final VoiceProvider tts = new VoiceProvider(API_KEY);
    protected Thread ThreadCreateAudioFileUS,
                     ThreadCreateAudioFileUK,
                     ThreadCreateAudioFileVN;
    private VoiceParameters paraUS, paraUK, paraVN;

    public class TaskCreateAudioFileUS extends Task<Void> {
        private String word;

        public TaskCreateAudioFileUS(String word) {
            this.word = word;
        }

        @Override
        protected Void call() throws Exception {
            try {
                createAudioFileUS(word);
                System.out.println("US");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class TaskCreateAudioFileUK extends Task<Void> {
        private String word;

        public TaskCreateAudioFileUK(String word) {
            this.word = word;
        }

        @Override
        protected Void call() throws Exception {
            try {
                createAudioFileUK(word);
                System.out.println("UK");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class TaskCreateAudioFileVN extends Task<Void> {
        private String word;

        public TaskCreateAudioFileVN(String word) {
            this.word = word;
        }

        @Override
        protected Void call() throws Exception {
            try {
                createAudioFileVN(word);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void createAudioFileUS(String word) {
        paraUS.setText(word);

        try {
            byte[] voice = tts.speech(paraUS);

            FileOutputStream fos = new FileOutputStream(voiceUS_WAV);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAudioFileUK(String word) {
        paraUK.setText(word);

        try {
            byte[] voice = tts.speech(paraUK);

            FileOutputStream fos = new FileOutputStream(voiceUK_WAV);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createAudioFileVN(String word) {
        paraUK.setText(word);

        try {
            byte[] voice = tts.speech(paraVN);

            FileOutputStream fos = new FileOutputStream(voiceVN_WAV);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void speakUSvoice() {
        if (ThreadCreateAudioFileUS.isAlive()) {
            try {
                ThreadCreateAudioFileUS.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(voiceUS_WAV.toURI().toString()));
        mediaPlayer.play();
    }

    public void speakUKvoice() {
        if (ThreadCreateAudioFileUK.isAlive()) {
            try {
                ThreadCreateAudioFileUK.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(voiceUK_WAV.toURI().toString()));
        mediaPlayer.play();
    }

    public void speakVNvoice() {
        if (ThreadCreateAudioFileVN.isAlive()) {
            try {
                ThreadCreateAudioFileVN.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(voiceVN_WAV.toURI().toString()));
        mediaPlayer.play();
    }

    public VoiceRSS() {
        if (paraUS == null) {
            paraUS = new VoiceParameters("Easter egg found", Languages.English_UnitedStates);
            paraUS.setCodec(AudioCodec.WAV);
            paraUS.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
            paraUS.setBase64(false);
            paraUS.setSSML(false);
            paraUS.setRate(0);
        }

        if (paraUK == null) {
            paraUK = new VoiceParameters("Tìm thấy trứng phục sinh", Languages.English_GreatBritain);
            paraUK.setCodec(AudioCodec.WAV);
            paraUK.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
            paraUK.setBase64(false);
            paraUK.setSSML(false);
            paraUK.setRate(0);
        }

        if (paraVN == null) {
            paraVN = new VoiceParameters("Easter egg found", Languages.Vietnamese);
            paraVN.setCodec(AudioCodec.WAV);
            paraVN.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
            paraVN.setBase64(false);
            paraVN.setSSML(false);
            paraVN.setRate(0);
        }
    }
}
