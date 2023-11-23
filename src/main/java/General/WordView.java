package General;

import Main.MainBoard;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WordView extends HBox {
    private char[] letters;
    public int correctedLetter = 0;
    private static Font wordFont;

    public WordView() {
        setAlignment(Pos.CENTER);
        wordFont = Font.loadFont(MainBoard.class.getResourceAsStream("/Media/Font/EmotionalBaggage-Regular.ttf"), 100);
    }

    public void setWord(String word) {
        letters = word.toCharArray();
        getChildren().clear();
        for (char c : letters) {
            Text t = new Text(c + "");
            t.setFont(wordFont);
            getChildren().add(t);
        }
        setPrefWidth(80 * letters.length);
    }

    public int getCorrectedLetter() {
        return correctedLetter;
    }

    public void setCorrectedLetter(int correctedLetter) {
        this.correctedLetter = correctedLetter;
    }

    public boolean handleKeyEvent(char letter) {
        if (letter == letters[correctedLetter]) {
            Text t = (Text) getChildren().get(correctedLetter);
            t.setFill(Color.GREEN);
            correctedLetter++;
        } else {
            return false;
        }
        return true;
    }

    public boolean isFinished() {
        return correctedLetter == letters.length;
    }
}
