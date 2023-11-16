package General;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Dictionary extends VoiceRSS {
    protected static ObservableList<String> dictionaryList = FXCollections.observableArrayList();
    protected static ObservableList<String> detailList = FXCollections.observableArrayList();
    protected static ObservableList<String> bookmarkedList = FXCollections.observableArrayList();
    protected static ObservableList<String> historyList = FXCollections.observableArrayList();
    protected ObservableList<String> searchListTemp = FXCollections.observableArrayList();

    public String getDetail(String word) {
        int idx = dictionaryList.indexOf(word);
        if (idx >= 0) return formatHtml(detailList.get(idx));
        throw new RuntimeException();
    }

    public void updateListView(String currentText, ObservableList<String> wordList, ListView listView) {
        searchListTemp.clear();
        Pattern pattern = Pattern.compile("^" + currentText, Pattern.CASE_INSENSITIVE);
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
        if (bookmarkedList.contains(word)) {
            bookmarkCheckBox.setSelected(true);
        } else {
            bookmarkCheckBox.setSelected(false);
        }
    }

    public void bookmarkWord(ListView listView, CheckBox bookmarkCheckBox) {
        String word = (String) listView.getSelectionModel().getSelectedItem();
        if (word != null) {
            if (bookmarkCheckBox.isSelected() && !bookmarkedList.contains(word)) {
                bookmarkedList.add(0, word);
            } else {
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
            BufferedReader wordReader = new BufferedReader(new FileReader("src/main/resources/Media/Text file/Words.txt"));
            String word = wordReader.readLine();
            while (word != null) {
                dictionaryList.add(word);
                word = wordReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedReader detailReader = new BufferedReader(new FileReader("src/main/resources/Media/Text file/Details.txt"));
            String detail = detailReader.readLine();
            while (detail != null) {
                detailList.add(detail);
                detail = detailReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedReader bookmarkReader = new BufferedReader(new FileReader("src/main/resources/Media/Text file/BookmarkWords.txt"));
            String word = bookmarkReader.readLine();
            while (word != null) {
                bookmarkedList.add(word);
                word = bookmarkReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedReader historyReader = new BufferedReader(new FileReader("src/main/resources/Media/Text file/HistoryWords.txt"));
            String word = historyReader.readLine();
            while (word != null) {
                historyList.add(word);
                word = historyReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String formatHtml(String inputHtml) {
        String formattedHtml = inputHtml.replaceAll("@(.*?)<br />", "<h1 style=\"color: red;\">$1</h1>")
                .replaceAll("\\*(.*?)<br />", "<h2 style=\"color: blue;\">$1</h2>")
                .replaceAll("- (.*?)<br />", "<p style=\"color: black;\">$1</p>")
                .replaceAll("=([^=]*?)<br />", "<p style=\"color: purple;\">$1</p>")
                .replaceAll("\\+", ":")
                .replaceAll("<br />", "<br /><br />")
                .replaceAll("<Q>", "")
                .replaceAll("</Q>", "");

        return formattedHtml;
    }

    public Dictionary() {
    }
}
