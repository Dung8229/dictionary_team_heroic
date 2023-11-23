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
    private double initTime;
    private double time;
    private DoubleProperty progressProperty = new SimpleDoubleProperty(.0);
    private BooleanProperty isRunningProperty = new SimpleBooleanProperty(false);
    private Timeline timeline;
    public CountdownTimer(double initTime) {
        this.initTime = initTime;
        time = initTime;
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {
            this.time -= 0.25;
            progressProperty.set(time * 1.00 / initTime);
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

    public void start() {
        if (timeline.getStatus() == Animation.Status.STOPPED) {
            timeline.play();
        }
    }
    
    public void add(double additionTime) {
        time += additionTime;
        if (time > initTime) time = initTime;
    }

    public void reset(double initTime) {
        this.initTime = initTime;
        time = initTime;
    }
}
