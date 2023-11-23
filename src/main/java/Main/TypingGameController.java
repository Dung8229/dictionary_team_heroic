package Main;

import General.CountdownTimer;
import General.Game;
import General.WordView;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class TypingGameController extends Game implements Initializable {
    @FXML
    private AnchorPane typingGamePane;
    private AnchorPane typedWordPane;
    @FXML
    private AnchorPane mainBoard;
    @FXML
    private AnchorPane infoPane;
    private TypedWordController typedWordController;
    @FXML
    private ProgressBar proBar;
    @FXML
    private Button playButton;
    @FXML
    private Button wordUnlockedButton;
    @FXML
    private Button backButton;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label highScoreValueLabel;
    @FXML
    private Label wordUnlockedValueLabel;
    @FXML
    private Label titleLabel;
    private WordView wordView = new WordView();

    private static ObservableList<Boolean> isShowedWordList = FXCollections.observableArrayList();
    private static ObservableList<String> typingWordList = FXCollections.observableArrayList();
    private int index = -1;
    private CountdownTimer timer = new CountdownTimer(8);
    private Font wordFont;
    private int bonusPoint = 5000;

    private Media BGMusic;
    private Media bonusSFX;
    private Media wordFinishedSFX;
    private Media wrongLetterSFX;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private BooleanProperty isPlayingProperty = new SimpleBooleanProperty(false);
    private BooleanProperty isRestingProperty = new SimpleBooleanProperty(true);
    private IntegerProperty score = new SimpleIntegerProperty(0);
    private int wordUnlocked;

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
            BufferedReader wordReader = new BufferedReader(new FileReader("src/main/resources/Media/Text file/Typing Game/TypingGameWords.txt"));
            String word = wordReader.readLine();
            while (word != null) {
                typingWordList.add(word.substring(1));
                if (word.charAt(0) == '1') {
                    isShowedWordList.add(true);
                    wordUnlocked++;
                } else {
                    isShowedWordList.add(false);
                }
                word = wordReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Music
        BGMusic = new Media(MainBoard.class.getResource("/Media/Audio/TypingGame/round and round.mp3").toString());
        bonusSFX = new Media(MainBoard.class.getResource("/Media/Audio/TypingGame/BonusSFX.mp3").toString());
        wordFinishedSFX = new Media(MainBoard.class.getResource("/Media/Audio/TypingGame/WordFinishedSFX.mp3").toString());
        wrongLetterSFX = new Media(MainBoard.class.getResource("/Media/Audio/TypingGame/WrongLetterSFX.mp3").toString());
        setBackgroundMusic(BGMusic);

        typingGamePane.getChildren().addAll(wordView);

        AnchorPane.setTopAnchor(wordView, 225.0);

        proBar.progressProperty().bind(timer.getProgressProperty());

        isPlayingProperty.bind(timer.getIsRunningProperty());
        alert.setTitle("Greenbook");
        alert.setHeaderText(null);

        isPlayingProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                Platform.runLater(() -> {
                    if (!isPlayingProperty.get() && !isRestingProperty.get()) {
                        stopBackgroundMusic();
                        new MediaPlayer(wrongLetterSFX).play();
                        alert.setContentText("Hết giờ!\n" + "Điểm số của bạn là: " + score.get() + ".");
                        alert.showAndWait().ifPresent(response -> {
                            updateHighScore();
                            updateUnlockedWord();
                            updateShowedWord();
                            OpenTypedWordPane();
                        });
                    }
                });
            }
        });

        mainBoard.visibleProperty().bind(mainBoard.disabledProperty().not());
        backButton.visibleProperty().bind(backButton.disabledProperty().not());
        playButton.visibleProperty().bind(isRestingProperty);
        wordUnlockedButton.visibleProperty().bind(isRestingProperty);
        infoPane.visibleProperty().bind(isRestingProperty);
        titleLabel.visibleProperty().bind(isRestingProperty);
        proBar.visibleProperty().bind(isPlayingProperty);
        scoreLabel.visibleProperty().bind(isPlayingProperty);

        wordFont = Font.loadFont(MainBoard.class.getResourceAsStream("/Media/Font/EmotionalBaggage-Regular.ttf"), 100);
        scoreLabel.setFont(wordFont);
        scoreLabel.textProperty().bind(score.asString());
        titleLabel.setFont(wordFont);
        titleLabel.setStyle("-fx-text-fill: #ff6a00");

        // Init highscore
        try {
            BufferedReader reader = new BufferedReader(new FileReader
                    ("src/main/resources/Media/Text file/Typing Game/TypingGameHighScore.txt"));
            highScoreValueLabel.setText(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Init unlocked word
        updateUnlockedWord();
    }
    @Override
    public void handleStartButton() {
        index = -1;
        bonusPoint = 5000;
        timer.reset(6);
        score.set(0);

        wordView.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (!wordView.handleKeyEvent(e.getCode().toString().charAt(0))) {
                    new MediaPlayer(wrongLetterSFX).play();
                    timer.add(-1.2);
                }
                if (wordView.isFinished()) {
                    new MediaPlayer(wordFinishedSFX).play();
                    wordView.setCorrectedLetter(0);
                    showNextWord();
                }
            }
        });

        isRestingProperty.set(false);

        timer.start();
        showNextWord();

        playBackgroundMusic(true);
    }
    @Override
    public void handlePauseButton() {}
    @Override
    public void handleQuitButton() {}

    public void handleBackButton() {
        mainBoard.getChildren().clear();
        mainBoard.setDisable(true);
        backButton.setDisable(true);
    }

    public void showNextWord() {
        if (index >= 0) {
            String finishedWord = typingWordList.get(index);
            if (isShowedWordList.get(index) == false) {
                isShowedWordList.set(index, true);
                wordUnlocked++;
            }
            score.set(score.get() + finishedWord.length() * 150);
            timer.add(0.3 * finishedWord.length());
            if (score.get() > bonusPoint) {
                new MediaPlayer(bonusSFX).play();
                timer.add(5);
                bonusPoint += 5000;
            }
        }

        index += rand(1, 50);
        if (index > typingWordList.size()) {
            index = rand(1, 50);
        }
        String word = typingWordList.get(index);
        wordView.setWord(word);
        AnchorPane.setLeftAnchor(wordView, (750 - wordView.getPrefWidth()) / 2);
    }

    public void updateShowedWord() {
        try {
            BufferedWriter typingGameWriter = new BufferedWriter(new FileWriter("src/main/resources/Media/Text file/Typing Game/TypingGameWords.txt"));
            for (int i = 0; i < typingWordList.size(); i++) {
                typingGameWriter.write(isShowedWordList.get(i) ? "1" : "0");
                typingGameWriter.write(typingWordList.get(i));
                typingGameWriter.newLine();
            }
            typingGameWriter.flush();
            typingGameWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateHighScore() {
        if (!Objects.equals(scoreLabel.getText(), "")) {
            if (Integer.parseInt(scoreLabel.getText()) > Integer.parseInt(highScoreValueLabel.getText())) {
                try {
                    BufferedWriter writer = new BufferedWriter(
                            new FileWriter("src/main/resources/Media/Text file/Typing Game/TypingGameHighScore.txt"));
                    writer.write(scoreLabel.getText());
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                highScoreValueLabel.setText(scoreLabel.getText());
            }
        }
    }

    public void updateUnlockedWord() {
        wordUnlockedValueLabel.setText(wordUnlocked + "/1156");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    private int rand(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public void OpenTypedWordPane() {
        isRestingProperty.set(true);
        wordView.getChildren().clear();
        wordView.setCorrectedLetter(0);
        mainBoard.setDisable(false);
        mainBoard.getChildren().setAll(typedWordPane);
        backButton.setDisable(false);
        mainBoard.getChildren().add(backButton);
        typedWordController.initWordList();
    }
}