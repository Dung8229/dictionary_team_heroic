package General;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

import javax.naming.Binding;

public class CountdownTimer {
    private double finishTime;
    private double time;
    private DoubleProperty progressProperty = new SimpleDoubleProperty(.0);
    private BooleanProperty isRunningProperty = new SimpleBooleanProperty(false);
    private Timeline timeline;
    public CountdownTimer(double finishTime) {
        this.finishTime = finishTime;
        time = finishTime;
        timeline = new Timeline(new KeyFrame(Duration.seconds(finishTime / 10), e -> {
            this.time -= finishTime / 10;
            progressProperty.set(time * 1.00 / finishTime);
            if (ring()) {
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        isRunningProperty.bind(Bindings.greaterThan(progressProperty, 0));
    }

    public boolean ring() {
        return time <= 0;
    }

    public DoubleProperty getProgressProperty() {
        return progressProperty;
    }

    public BooleanProperty getIsRunningProperty() {
        return isRunningProperty;
    }

    public void start(double finishTime) {
        if (timeline.getStatus() == Animation.Status.STOPPED) {
            timeline.play();
        }
    }

    public void reset(double finishTime) {
        this.finishTime = finishTime;
        time = finishTime;
    }
}
