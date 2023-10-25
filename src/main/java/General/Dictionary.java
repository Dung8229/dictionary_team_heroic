package General;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dictionary {
    private static final String GET_WORD_QUERY = "SELECT * FROM tbl_edict";
    protected ObservableList<String> searchList = FXCollections.observableArrayList();
    protected ObservableList<String> detailList = FXCollections.observableArrayList();
    protected ObservableList<String> searchListTemp = FXCollections.observableArrayList();

    public String getDetail(String word) {
        int idx = searchList.indexOf(word);
        return detailList.get(idx);
    }

    public void setSearchListTemp(String wordPattern) {
        searchListTemp.clear();
        Pattern pattern = Pattern.compile("^" + wordPattern, Pattern.CASE_INSENSITIVE);
        for (String word : searchList) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.find()) {
                searchListTemp.add(word);
            }
        }
    }

    public Dictionary() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(GET_WORD_QUERY);
            while (queryOutput.next()) {
                searchList.add(queryOutput.getString("word"));
                detailList.add(queryOutput.getString(("detail")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
