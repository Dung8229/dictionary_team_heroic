package QuizQuestion;

public class Choice {
    private String id;
    private String content;
    private boolean correct;
    private String questionId;

    public Choice(String id, String content, boolean correct, String questionId) {
        this.id = id;
        this.content = content;
        this.correct = correct;
        this.questionId = questionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
