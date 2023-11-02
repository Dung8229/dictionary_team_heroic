package General;

public class Word implements Comparable<Word> {
    String word;
    String details;

    public Word(String word, String details) {
        this.word = word;
        this.details = details;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public int compareTo(Word other) {
        return word.compareTo(other.word);
    }
}
