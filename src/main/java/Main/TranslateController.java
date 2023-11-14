package Main;

import General.Dictionary;
import General.TranslateAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.Objects;

public class TranslateController extends Dictionary {
    @FXML
    private TextArea inputArea;
    @FXML
    private TextArea outputArea;

    @Override
    public void speakUSvoice() {
        if (!Objects.equals(inputArea.getText(), "")) {
            ThreadCreateAudioFileUS = new Thread(new TaskCreateAudioFileUS(inputArea.getText()));
            ThreadCreateAudioFileUS.start();
            super.speakUSvoice();
        }
    }

    @Override
    public void speakVNvoice() {
        if (!Objects.equals(outputArea.getText(), "")) {
            ThreadCreateAudioFileVN = new Thread(new TaskCreateAudioFileVN(outputArea.getText()));
            ThreadCreateAudioFileVN.start();
            super.speakVNvoice();
        }
    }

    public void translate() throws IOException {
        if (!Objects.equals(inputArea.getText(), "")) {
            outputArea.setText(TranslateAPI.translate("en", "vi", inputArea.getText()));
//            ThreadCreateAudioFileUS = new Thread(new TaskCreateAudioFileUS(inputArea.getText()));
//            ThreadCreateAudioFileVN = new Thread(new TaskCreateAudioFileVN(outputArea.getText()));
//            ThreadCreateAudioFileUS.start();
//            ThreadCreateAudioFileVN.start();
        }
    }
}
