package Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    HBox wordBox1;
    @FXML
    HBox wordBox2;
    @FXML
    HBox wordBox3;
    protected static ObservableList<String> dictionaryList = FXCollections.observableArrayList();

    private int index = -1;

    public void showNextWord() {
        index += rand(1, 50);
        String word = dictionaryList.get(index);
        wordBox1.getChildren().clear();
        for (char c : word.toCharArray()) {
            Text letter = new Text(c + "");
            wordBox1.getChildren().add(letter);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            BufferedReader wordReader = new BufferedReader(new FileReader("src/main/resources/Media/Text file/TypingGameWords.txt"));
            String word = wordReader.readLine();
            while (word != null) {
                dictionaryList.add(word);
                word = wordReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int rand(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
