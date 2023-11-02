package General;

import Main.MainBoard;
import Main.SearchPane;
import com.voicerss.tts.Languages;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileOutputStream;

public class VoiceRSS {
    private static final String API_KEY = "d3bf64e482e14c818dcfb90f0f861ecd";
    private static final File voiceUS_WAV = new File("src/main/resources/Media/Audio/VoiceUS.wav");
    private static final File voiceUK_WAV = new File("src/main/resources/Media/Audio/VoiceUK.wav");
    private static final VoiceProvider tts = new VoiceProvider(API_KEY);

    private VoiceParameters paraUS, paraUK;
    private SearchPane searchPane;

    public class TaskCreateAudioFileUS extends Task<Void> {
        private String word;

        public TaskCreateAudioFileUS(String word) {
            this.word = word;
        }

        @Override
        protected Void call() throws Exception {
            try {
                createAudioFileUS(word);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void createAudioFileUS(String word) {
        paraUS.setText(word);

        try {
            paraUS.setLanguage(Languages.English_UnitedStates);
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
        try {
            paraUK.setLanguage(Languages.English_GreatBritain);
            byte[] voice = tts.speech(paraUK);

            FileOutputStream fos = new FileOutputStream(voiceUK_WAV);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void speakUSvoice() {
//        try {
//            threadCreateAudioFile.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        MediaPlayer mediaPlayer = new MediaPlayer(new Media(voiceUS_WAV.toURI().toString()));
//        mediaPlayer.play();
//    }
//
//    public void speakUKvoice() {
//        try {
//            threadCreateAudioFile.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        MediaPlayer mediaPlayer = new MediaPlayer(new Media(voiceUK_WAV.toURI().toString()));
//        mediaPlayer.play();
//    }

    public VoiceRSS() {
        try {
            searchPane = FXMLLoader.load(MainBoard.class.getResource("/fxml/SearchPane.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
