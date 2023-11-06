package General;

import com.voicerss.tts.VoiceProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dictionary extends VoiceRSS {
    private static final String GET_WORD_QUERY = "SELECT * FROM tbl_edict";
    protected static ObservableList<String> dictionaryList = FXCollections.observableArrayList();
    protected static ObservableList<String> detailList = FXCollections.observableArrayList();
    protected ObservableList<String> searchListTemp = FXCollections.observableArrayList();
    protected static ObservableList<Boolean> isBookmarkedList = FXCollections.observableArrayList();
    protected static ObservableList<String> bookmarkedList = FXCollections.observableArrayList();
    protected static ObservableList<String> historyList = FXCollections.observableArrayList();

    public String getDetail(String word) {
        int idx = dictionaryList.indexOf(word);
        return detailList.get(idx);
    }

    public void updateListView(String wordPattern, ObservableList<String> wordList, ListView listView) {
        searchListTemp.clear();
        Pattern pattern = Pattern.compile("^" + wordPattern, Pattern.CASE_INSENSITIVE);
        for (String word : wordList) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.find()) {
                searchListTemp.add(word);
            }
        }
        listView.setItems(searchListTemp);
    }

    public void addHistoryList(String word) {
        if (!historyList.contains(word)) {
            historyList.add(0, word);
        } else {
            historyList.remove(word);
            historyList.add(0, word);
        }
    }

    public void initWordList(ObservableList<String> wordList, ListView listView) {
        listView.setItems(wordList);
    }

    public void handleBookmarkCheckBoxSelected(CheckBox bookmarkCheckBox, String word) {
        if (isBookmarkedList.get(dictionaryList.indexOf(word)) == false) {
            bookmarkCheckBox.setSelected(false);
        } else {
            bookmarkCheckBox.setSelected(true);
        }
    }

    public void bookmarkWord(ListView listView, CheckBox bookmarkCheckBox) {
        String word = (String) listView.getSelectionModel().getSelectedItem();
        if (word != null) {
            int index = dictionaryList.indexOf(word);
            if (bookmarkCheckBox.isSelected() && isBookmarkedList.get(index) == false) {
                isBookmarkedList.set(index, true);
                bookmarkedList.add(0, word);
            } else {
                isBookmarkedList.set(index, false);
                bookmarkedList.remove(word);
            }
        }
    }

    public static void saveSettingToFile() {
        try {
            BufferedWriter bookmarkWriter = new BufferedWriter(new FileWriter("src/main/resources/Media/Text file/BookmarkWords.txt"));
            for (String s : bookmarkedList) {
                bookmarkWriter.write(s);
                bookmarkWriter.newLine();
            }
            bookmarkWriter.flush();
            bookmarkWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedWriter bookmarkWriter = new BufferedWriter(new FileWriter("src/main/resources/Media/Text file/HistoryWords.txt"));
            for (String s : historyList) {
                bookmarkWriter.write(s);
                bookmarkWriter.newLine();
            }
            bookmarkWriter.flush();
            bookmarkWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getSettingFromFile() {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(GET_WORD_QUERY);
            while (queryOutput.next()) {
                dictionaryList.add(queryOutput.getString("word"));
                detailList.add(queryOutput.getString("detail"));
                isBookmarkedList.add(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedReader bookmarkReader = new BufferedReader(new FileReader("src/main/resources/Media/Text file/BookmarkWords.txt"));
            String word = bookmarkReader.readLine();
            while (word != null) {
                isBookmarkedList.set(dictionaryList.indexOf(word), true);
                bookmarkedList.add(word);
                word = bookmarkReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedReader bookmarkReader = new BufferedReader(new FileReader("src/main/resources/Media/Text file/HistoryWords.txt"));
            String word = bookmarkReader.readLine();
            while (word != null) {
                historyList.add(word);
                word = bookmarkReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Dictionary() {
    }
}
