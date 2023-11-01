package Main;

import General.Dictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class RunDictionary extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Dictionary.getSettingFromFile();
        FXMLLoader fxmlLoader = new FXMLLoader(MainBoard.class.getResource("/fxml/MainBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Image icon = new Image(getClass().getResourceAsStream("/Media/Image/icon.jpg"));
        stage.getIcons().add(icon);
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> Dictionary.saveSettingToFile());
    }

    public static void main(String[] args) {
        launch();
    }


}
