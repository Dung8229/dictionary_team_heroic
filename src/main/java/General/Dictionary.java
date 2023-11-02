package General;

import com.voicerss.tts.VoiceProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dictionary {
    private static final String API_KEY = "d3bf64e482e14c818dcfb90f0f861ecd";
    private static final File voiceUS_WAV = new File("src/main/resources/Media/Audio/VoiceUS.wav");
    private static final File voiceUK_WAV = new File("src/main/resources/Media/Audio/VoiceUK.wav");
    private static final VoiceProvider tts = new VoiceProvider(API_KEY);

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

    public void initWordList(ObservableList<String> wordList, ListView listView) {
        listView.setItems(wordList);
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
    }

    public static void getSettingFromFile() {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            try {
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
    }

    public Dictionary() {
    }
}
