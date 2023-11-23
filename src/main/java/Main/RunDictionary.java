package Main;

import General.Dictionary;
import General.TranslateAPI;
import QuizQuestion.JdbcUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

public class RunDictionary extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Dictionary.getSettingFromFile();
        FXMLLoader fxmlLoader = new FXMLLoader(MainBoard.class.getResource("/fxml/MainBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Image icon = new Image(getClass().getResourceAsStream("/Media/Image/GreenBookIcon.jpg"));
        scene.getStylesheets().add(getClass().getResource("/Style/style.css").toExternalForm());
//        scene.getStylesheets().add(getClass().getResource("/Style/webViewstyle.css").toExternalForm());
//        scene.getStylesheets().add(getClass().getResource("/Style/gamestyle.css").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("GreenBook");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        stage.setOnHidden(et -> {
            Connection conn = JdbcUtils.getConnection();
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException ex) {

                }
        });

        stage.setOnCloseRequest(event -> Dictionary.saveSettingToFile());
    }

    public static void main(String[] args) {
        launch();
    }


}
