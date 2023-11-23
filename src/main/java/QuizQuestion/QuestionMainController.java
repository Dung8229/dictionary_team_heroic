package QuizQuestion;

import General.Game;
import Main.MainBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class QuestionMainController implements Initializable {
    @FXML
    private Label label;
    @FXML
    private Button quitButton;
    @FXML
    private AnchorPane mainBoard;
    @FXML
    private AnchorPane questionPractice;
    @FXML
    private AnchorPane questionManage;
    public void setMainBoard(AnchorPane pane) {
        mainBoard.getChildren().setAll(pane);
    }
    public void OpenQuestionMain() {
        mainBoard.setDisable(true);
        mainBoard.setVisible(false);
    }
    public void OpenQuestionManage() {
        mainBoard.setDisable(false);
        mainBoard.setVisible(true);
        questionManage.getChildren().addAll(quitButton);
        setMainBoard(questionManage);

    }
    public void OpenQuestionPractice() {
        mainBoard.setDisable(false);
        mainBoard.setVisible(true);
        questionPractice.getChildren().addAll(quitButton);
        setMainBoard(questionPractice);
    }
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Click me!");
        label.setText("Hehee");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader= new FXMLLoader(MainBoard.class.getResource("/fxml/QuestionManage.fxml"));
            questionManage = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader= new FXMLLoader(MainBoard.class.getResource("/fxml/QuestionPractice.fxml"));
            questionPractice = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainBoard.setDisable(true);
    }
}
