package General;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public abstract class Game {
    MediaPlayer mediaPlayer = null;

    public abstract void init();
    public abstract void handleStartButton();
    public abstract void handlePauseButton();
    public abstract void handleQuitButton();

    void setBackgroundMusic(Media backgroundMusic) {
        MediaPlayer mediaPlayer= new MediaPlayer(backgroundMusic);
    }

    void playBackgroundMusic(boolean isLoop) {
        if (isLoop) {
            mediaPlayer.setCycleCount(-1);
        } else {
            mediaPlayer.setCycleCount(1);
        }
        mediaPlayer.play();
    }

    void pauseBackgroundMusic() {
        mediaPlayer.pause();
    }

    void unpauseBackgroundMusic() {
        mediaPlayer.play();
    }

    void stopBackgroundMusic() {
        mediaPlayer.stop();
    }
}
