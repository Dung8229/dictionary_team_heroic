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
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Click me!");
        label.setText("Hehee");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void practiceHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(QuestionMainController.class.getResource("/fxml/QuestionPractice.fxml"));
        Parent root = loader.load();

        QuestionPracticeController controller = loader.getController();
        TextInputDialog inp = new TextInputDialog();
        inp.setHeaderText("The number of questions to practice");
        Optional<String> num = inp.showAndWait();
        if (num.isPresent()) {
            controller.setNumOfQuestion(Integer.parseInt(num.get()));
            controller.loadQuestion();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void manageHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(QuestionMainController.class.getResource("/fxml/QuestionManage.fxml"));

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
