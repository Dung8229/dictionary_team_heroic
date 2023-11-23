package QuizQuestion;

import General.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionPracticeController implements Initializable {
    @FXML
    private Label lblContent;
    @FXML
    private RadioButton rdoA;
    @FXML
    private RadioButton rdoB;
    @FXML
    private RadioButton rdoC;
    @FXML
    private RadioButton rdoD;
    private int numOfQuestion;

    private List<Question> questions;
    private int currentIdx = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loadQuestion() {

    }

    public void checkAnswerHandler(ActiveEvent event) {

    }

    public void nextHandler(ActiveEvent event) {

    }

    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    public void setNumOfQuestion(int numOfQuestion) {
        this.numOfQuestion = numOfQuestion;
    }

}
