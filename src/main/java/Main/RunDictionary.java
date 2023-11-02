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
        System.out.println();
        FXMLLoader fxmlLoader = new FXMLLoader(MainBoard.class.getResource("/fxml/MainBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Image icon = new Image(getClass().getResourceAsStream("/Media/Audio/Image/icon.jpg"));
        scene.getStylesheets().add(getClass().getResource("/Style/style.css").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
