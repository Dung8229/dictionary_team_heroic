package QuizQuestion;

import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {
    public static List<Category> getCategories() throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM category");

        List<Category> results = new ArrayList<>();
        while (rs.next()) {
            Category c = new Category(rs.getInt("id"), rs.getString("name"));
            results.add(c);
        }

        return results;
    }

    public static Category getCategoryById(int id) throws SQLException {
        String sql = "SELECT * FROM category WHERE id=?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, id);

        ResultSet rs = stm.executeQuery();
        Category c = null;
        while (rs.next()) {
            c = new Category(rs.getInt("id"), rs.getString("name"));
            break;
        }
        return c;
    }

    public static List<Question> getQuestions(String keyword) throws SQLException {
        String sql = "SELECT * FROM question";
        if (!keyword.isEmpty()) sql += "WHERE content LIKE ?";

        Connection conn = JdbcUtils.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        if (!keyword.isEmpty())
            stm.setString(1, String.format("%%%s%%", keyword));

        ResultSet rs = stm.executeQuery();

        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            Question q = new Question(rs.getString("id"),
                    rs.getString("content"), rs.getInt("category_id"));
            List<Choice> choices = getChoicesByQuestion(q.getId());
            q.setChoices(choices);

            questions.add(q);
        }
        return questions;
    }

    public static List<Choice> getChoicesByQuestion(String questionId) throws SQLException {
        String sql = "SELECT * FROM choice WHERE question_id=?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, questionId);

        ResultSet rs = stm.executeQuery();

        List<Choice> choices = new ArrayList<>();
        while (rs.next()) {
            Choice c = new Choice(rs.getString("id"), rs.getString("content"),
                    rs.getBoolean("is_correct"), rs.getString("question_id"));
            choices.add(c);
        }

        return choices;
    }

    public static void addOrUpdateQuestion(Question question, ArrayList<Choice> choices,
                                           String sql1, String sql2) throws SQLException {
        Connection conn = JdbcUtils.getConnection();

        conn.setAutoCommit(false);

        //add question
        PreparedStatement stm = conn.prepareStatement(sql1);
        stm.setString(1, question.getContent());
        stm.setInt(2, question.getCategoryId());
        stm.setString(3, question.getId());


        stm.executeUpdate();

        for (Choice c: choices) {
            PreparedStatement st = conn.prepareCall(sql2);
            st.setString(1, c.getContent());
            st.setBoolean(2, c.isCorrect());
            st.setString(3, question.getId());
            st.setString(4, c.getId());

            st.executeUpdate();
        }
        conn.commit();

    }

    public static void updateQuestion(Question question, ArrayList<Choice> choices) throws SQLException {
        String sql1 = "UPDATE question Set content=?, category_id=? WHERE id=?";
        String sql2 = "UPDATE choice Set content=?, is_correct=?, question_id=? WHERE id=?";

        addOrUpdateQuestion(question, choices, sql1, sql2);
    }
    public static void addQuestion(Question question, ArrayList<Choice> choices) throws SQLException {
        String sql1 = "INSERT INTO question(content, category_id, id) VALUES(?, ?, ?)";
        String sql2 = "INSERT INTO choice(content, is_correct, question_id, id) VALUES(?, ?, ?, ?)";

        addOrUpdateQuestion(question, choices, sql1, sql2);
    }

    public static void deleteQuestion(String questionId) throws SQLException {
        String sql1 = "DELETE FROM choice WHERE question_id=?";
        String sql2 = "DELETE FROM question WHERE id=?";
        Connection conn = JdbcUtils.getConnection();
        conn.setAutoCommit(false);

        //delete choice of the question
        PreparedStatement stm1 = conn.prepareStatement(sql1);
        stm1.setString(1, questionId);
        stm1.executeUpdate();

        PreparedStatement stm2 = conn.prepareStatement(sql2);
        stm2.setString(1, questionId);
        stm2.executeUpdate();

        conn.commit();
    }

    public static Alert getAlertInfo(String content, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setContentText(content);

        return a;
    }
}
