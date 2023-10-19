package General;

public class Word implements Comparable {
    enum WordType {
        NOUN,
        PRONOUN,
        VERB,
        ADJECTIVE,
        ADVERB,
        PREPOSITION,
        CONJUNCTION,
        INTERJECTION
    }
    String word;
    WordType wordType;
    String pronunciation;

    @Override
    public int compareTo(Object o) {
        if (o instanceof Word other) {
            return word.compareTo(other.word);
        }
        return -1;
    }
}
