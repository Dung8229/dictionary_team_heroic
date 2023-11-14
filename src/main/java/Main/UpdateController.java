package Main;

import General.Dictionary;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

import java.util.Objects;
import java.util.regex.Pattern;

public class UpdateController extends Dictionary {
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private TextField searchField;
    @FXML
    private Button removeButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button saveButton;

    private Pattern wordPattern = Pattern.compile("^[\\w|\\s|-]*$", Pattern.CASE_INSENSITIVE);
    private static final String NEW_WORD_DETAIL = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><h2><span style=\"background-color: rgb(255, 255, 255); color: rgb(204, 51, 51); font-family: &quot;&quot;; font-size: x-large;\">Từ:</span></h2><p><h3><ul><li><span style=\"background-color: rgb(255, 255, 255); color: rgb(51, 77, 179); font-size: large;\">Loại từ:</span></li></ul><p><ul><h4><ul><li><span style=\"background-color: rgb(255, 255, 255); font-size: medium;\">Định nghĩa:</span></li></ul></h4><h4><ul><li><span style=\"background-color: rgb(255, 255, 255); caret-color: rgb(77, 51, 153); color: rgb(77, 51, 153); font-size: medium;\">Ví dụ:</span></li></ul></h4></ul></p></h3></p><p><h3><h3><ul><li><span style=\"background-color: rgb(255, 255, 255); color: rgb(51, 77, 179); font-size: large;\">Loại từ 2:</span></li></ul><p><h4><ul><ul><li><span style=\"background-color: rgb(255, 255, 255); font-size: medium;\">Định nghĩa:</span></li></ul></ul></h4></p></h3></h3></p><h3><h4><ul style=\"font-weight: 700;\"><h4><ul><li><span style=\"background-color: rgb(255, 255, 255); caret-color: rgb(77, 51, 153); color: rgb(77, 51, 153); font-size: medium;\">Ví dụ:</span></li></ul></h4></ul></h4></h3><br></body></html>";
    private String word;

    public void loadDetail() {
        word = searchField.getText();
        if (Objects.equals(word, "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dictionary");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa điền từ!");
            alert.showAndWait();
            resetButton.setDisable(true);
            saveButton.setDisable(true);
            removeButton.setDisable(true);
            htmlEditor.setHtmlText("");
            return;
        }
        if (!wordPattern.matcher(word).find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dictionary");
            alert.setHeaderText(null);
            alert.setContentText("Từ chỉ chứa các chữ cái, chữ số, dấu cách và gạch ngang!");
            alert.showAndWait();
            resetButton.setDisable(true);
            saveButton.setDisable(true);
            removeButton.setDisable(true);
            htmlEditor.setHtmlText("");
            return;
        }
        try {
            htmlEditor.setHtmlText(getDetail(word));
            removeButton.setDisable(false);
        } catch (RuntimeException e) {
            htmlEditor.setHtmlText(NEW_WORD_DETAIL);
            removeButton.setDisable(true);
        }
        resetButton.setDisable(false);
        saveButton.setDisable(false);
    }

    public void saveChanges() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dictionary");
        alert.setHeaderText(null);
        if (!dictionaryList.contains(word)) {
            dictionaryList.add(binarySearch(dictionaryList, word), word);
            detailList.add(dictionaryList.indexOf(word), htmlEditor.getHtmlText());
            alert.setContentText("Thêm từ thành công");
            removeButton.setDisable(false);
        } else {
            detailList.set(dictionaryList.indexOf(word), htmlEditor.getHtmlText());
            alert.setContentText("Sửa từ thành công");
        }
        alert.showAndWait();
    }

    public void removeWord() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dictionary");
        alert.setHeaderText(null);
        alert.setContentText("Xóa \"" + word + "\" khỏi từ điển?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                dictionaryList.remove(word);
                removeButton.setDisable(true);
            }
        });
    }

    public void resetDetail() {
        word = searchField.getText();
        try {
            htmlEditor.setHtmlText(getDetail(word));
        } catch (RuntimeException e) {
            htmlEditor.setHtmlText(NEW_WORD_DETAIL);
        }
    }

    private int binarySearch(ObservableList<String> list, String word) {
        if (list.contains(word)) return list.indexOf(word);
        int l = 0,
                r = list.size() - 1;
        while (l <= r && list.get(l).compareTo(word) < 0) {
            int m = (l + r) / 2;
            if (list.get(m).compareTo(word) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return l;
    }
}
