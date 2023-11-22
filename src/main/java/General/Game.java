package General;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public abstract class Game {
    private MediaPlayer mediaPlayer = null;

    public abstract void init();
    public abstract void handleStartButton();
    public abstract void handlePauseButton();
    public abstract void handleQuitButton();

    protected void setBackgroundMusic(Media backgroundMusic) {
        mediaPlayer = new MediaPlayer(backgroundMusic);
    }

    protected void playBackgroundMusic(boolean isLoop) {
        if (isLoop) {
            mediaPlayer.setCycleCount(-1);
        } else {
            mediaPlayer.setCycleCount(1);
        }
        mediaPlayer.play();
    }

    protected void pauseBackgroundMusic() {
        mediaPlayer.pause();
    }

    protected void unpauseBackgroundMusic() {
        mediaPlayer.play();
    }

    protected void stopBackgroundMusic() {
        mediaPlayer.stop();
    }
}
