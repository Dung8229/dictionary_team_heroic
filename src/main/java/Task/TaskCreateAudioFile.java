package Task;

import Main.SearchPane;
import javafx.concurrent.Task;

public class TaskCreateAudioFile extends Task<Void> {
    private String word;

    public TaskCreateAudioFile(String word) {
        this.word = word;
    }

    @Override
    protected Void call() throws Exception {
        try {
            SearchPane.createAudioFile(word);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
