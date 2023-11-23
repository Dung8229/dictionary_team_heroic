package Main;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainBoard implements Initializable {
    @FXML
    private AnchorPane informationPane;
    
    @FXML
    private AnchorPane mainBoard;
    @FXML
    private AnchorPane searchPane;
    @FXML
    private AnchorPane translatePane;
    @FXML
    private AnchorPane bookmarkPane;
    @FXML
    private AnchorPane historyPane;
    @FXML
    private AnchorPane updatePane;
    @FXML
    private AnchorPane questionManage;
    @FXML
    private AnchorPane questionMain;
    @FXML
    private AnchorPane questionPractice;
    @FXML
    private AnchorPane menuPane, blendPane, touchPane;
    @FXML
    private Button menu, searchButton, translateButton, savedListButton;

    public void setMainBoard(AnchorPane pane) {
        mainBoard.getChildren().setAll(pane);
    }

    public void OpenSearchPane() {
        setMainBoard(searchPane);
    }
    public void OpenTranslatePane() {
        setMainBoard(translatePane);
    }
    public void OpenHistoryPane() {
        setMainBoard(historyPane);
    }
    public void OpenBookmarkPane() {
        setMainBoard(bookmarkPane);
    }
    public void OpenUpdatePane() {
        setMainBoard(updatePane);
    }
    public void OpenInformationPane() {
        setMainBoard(informationPane);
    }
    public void OpenQuestionMain() {
        setMainBoard(questionMain);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            searchPane = FXMLLoader.load(MainBoard.class.getResource("/fxml/SearchPane.fxml"));
            translatePane = FXMLLoader.load(MainBoard.class.getResource("/fxml/TranslatePane.fxml"));
            updatePane = FXMLLoader.load(MainBoard.class.getResource("/fxml/UpdatePane.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(MainBoard.class.getResource("/fxml/BookmarkPane.fxml"));
            bookmarkPane = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(MainBoard.class.getResource("/fxml/HistoryPane.fxml"));
            historyPane = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(MainBoard.class.getResource("/fxml/InformationPane.fxml"));
            informationPane = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader= new FXMLLoader(MainBoard.class.getResource("/fxml/QuestionMain.fxml"));
            questionMain = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        setMainBoard(informationPane);

        blendPane.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), blendPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), menuPane);
        translateTransition.setByX(-300);
        translateTransition.play();

        final boolean[] check = {true};
        menu.setOnMouseClicked(event -> {
            if (check[0]) {
                blendPane.setVisible(true);
                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), blendPane);
                fadeTransition1.setFromValue(0);
                fadeTransition1.setToValue(0.15);
                fadeTransition1.play();

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), menuPane);
                translateTransition1.setByX(+300);
                translateTransition1.play();
                check[0] = false;
            } else {
                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), blendPane);
                fadeTransition1.setFromValue(0.15);
                fadeTransition1.setToValue(0);
                fadeTransition1.play();

                fadeTransition1.setOnFinished(event1 -> {
                    blendPane.setVisible(false);
                });

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), menuPane);
                translateTransition1.setByX(-300);
                translateTransition1.play();
                check[0] = true;
            }
        });

        blendPane.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), blendPane);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                blendPane.setVisible(false);
            });

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), menuPane);
            translateTransition1.setByX(-300);
            translateTransition1.play();
            check[0] = true;
        });

        touchPane.setOnMouseEntered(event -> {
            if (check[0] && !blendPane.isVisible()) {
                blendPane.setVisible(true);
                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), blendPane);
                fadeTransition1.setFromValue(0);
                fadeTransition1.setToValue(0.15);
                fadeTransition1.play();

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), menuPane);
                translateTransition1.setByX(+300);
                translateTransition1.play();
                check[0] = false;
            }
        });
    }

}