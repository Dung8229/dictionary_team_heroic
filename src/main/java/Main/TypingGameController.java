package Main;

import General.CountdownTimer;
import General.Game;
import General.WordView;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class TypingGameController extends Game implements Initializable {
    @FXML
    private AnchorPane typingGamePane;
    private AnchorPane typedWordPane;
    @FXML
    private AnchorPane mainBoard;
    private TypedWordController typedWordController;
    @FXML
    private ProgressBar proBar;
    @FXML
    private Button playButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button backButton;
    private WordView wordView = new WordView();
    protected static ObservableList<String> typingWordList = FXCollections.observableArrayList();
    private int index = -1;
    private CountdownTimer timer = new CountdownTimer(3);
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private BooleanProperty isPlayingProperty = new SimpleBooleanProperty(false);
    private BooleanProperty isRestingProperty = new SimpleBooleanProperty(true);

    public TypingGameController() {
        System.out.println("Game Controller Checked!");
    }

    @Override
    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TypedWordPane.fxml"));
            typedWordPane = loader.load();
            typedWordController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader wordReader = new BufferedReader(new FileReader("src/main/resources/Media/Text file/TypingGameWords.txt"));
            String word = wordReader.readLine();
            while (word != null) {
                typingWordList.add(word);
                word = wordReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        typingGamePane.getChildren().addAll(wordView);

        AnchorPane.setTopAnchor(wordView, 225.0);

        proBar.progressProperty().bind(timer.getProgressProperty());

        isPlayingProperty.bind(timer.getIsRunningProperty());
        alert.setTitle("Greenbook");
        alert.setHeaderText(null);
        alert.setContentText("Hết giờ!");

        isPlayingProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                Platform.runLater(() -> {
                    if (!isPlayingProperty.get() && !isRestingProperty.get()) {
                        alert.showAndWait().ifPresent(response -> {
                            isRestingProperty.set(true);
                            wordView.getChildren().clear();
                            wordView.setCorrectedLetter(0);
                            mainBoard.setDisable(false);
                            mainBoard.getChildren().setAll(typedWordPane);
                            backButton.setVisible(true);
                            backButton.setDisable(false);
                            mainBoard.getChildren().add(backButton);
                            typedWordController.initWordList();
                        });
                    }
                });
            }
        });

        playButton.visibleProperty().bind(isRestingProperty);
        quitButton.visibleProperty().bind(isRestingProperty);
        proBar.visibleProperty().bind(isPlayingProperty);
        System.out.println("??");
    }
    @Override
    public void handleStartButton() {
        index = -1;

        wordView.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                wordView.handleKeyEvent(e.getCode().toString().charAt(0));
                if (wordView.isFinished()) {
                    wordView.setCorrectedLetter(0);
                    showNextWord();
                }
            }
        });

        isRestingProperty.set(false);

        timer.start(3);
        showNextWord();
    }
    @Override
    public void handlePauseButton() {}
    @Override
    public void handleQuitButton() {
        System.out.println("quit");
    }

    public void handleBackButton() {
        mainBoard.getChildren().clear();
        mainBoard.setDisable(true);
    }

    public void showNextWord() {
        index += rand(1, 50);
        String word = typingWordList.get(index);
        typedWordController.add(word);
        wordView.setWord(word);
        AnchorPane.setLeftAnchor(wordView, (750 - wordView.getPrefWidth()) / 2);

        timer.reset(3);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    private int rand(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}