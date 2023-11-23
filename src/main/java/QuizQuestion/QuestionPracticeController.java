package QuizQuestion;

import General.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
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
    @FXML
    private Button btnNext;
    private int numOfQuestion = 10;
    private List<Question> questions;
    private int currentIdx;
    private int score = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadQuestion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showQuestion() {
        Question q = this.questions.get(this.currentIdx);
        this.lblContent.setText(q.getContent());
        this.rdoA.setText(q.getChoices().get(0).getContent());
        this.rdoB.setText(q.getChoices().get(1).getContent());
        this.rdoC.setText(q.getChoices().get(2).getContent());
        this.rdoD.setText(q.getChoices().get(3).getContent());
    }
    public void loadQuestion() throws SQLException {
        this.questions = Utils.getQuestions("", this.numOfQuestion);
        this.showQuestion();
    }

    public void checkAnswerHandler(ActionEvent event) {
        Question q = this.questions.get(this.currentIdx);
        if ((q.getChoices().get(0).isCorrect() && this.rdoA.isSelected()) ||
                (q.getChoices().get(1).isCorrect() && this.rdoB.isSelected()) ||
                (q.getChoices().get(2).isCorrect() && this.rdoC.isSelected()) ||
                (q.getChoices().get(3).isCorrect() && this.rdoD.isSelected())) {
            Utils.getAlertInfo("EXACTLY!!!", Alert.AlertType.INFORMATION).show();
            score++;
        } else {
            Utils.getAlertInfo("FAILED!!!", Alert.AlertType.WARNING).show();
        }
    }

    public void nextHandler(ActionEvent event) {
        this.currentIdx++;
        if (this.currentIdx == this.questions.size()) {
            Utils.getAlertInfo("Your score: " + this.score, Alert.AlertType.INFORMATION).show();
            this.btnNext.setDisable(true);
        } else {
            this.rdoA.setSelected(false);
            this.rdoB.setSelected(false);
            this.rdoC.setSelected(false);
            this.rdoD.setSelected(false);
            this.showQuestion();
        }
    }

    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    public void setNumOfQuestion(int numOfQuestion) {
        this.numOfQuestion = numOfQuestion;
    }
}
