package QuizQuestion;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.PropertyPermission;
import java.util.ResourceBundle;
import java.util.UUID;

public class QuestionManageController implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private TextField txtContent;
    @FXML
    private ComboBox<Category> cbCategories;
    @FXML
    private TextField txtA;
    @FXML
    private TextField txtB;
    @FXML
    private TextField txtC;
    @FXML
    private TextField txtD;
    @FXML
    private RadioButton rdoA;
    @FXML
    private RadioButton rdoB;
    @FXML
    private RadioButton rdoC;
    @FXML
    private RadioButton rdoD;
    @FXML
    private TextField txtKeywords;
    @FXML
    private TableView<Question> tbQuestion;
    @FXML
    private Button btnUpdate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cbCategories.prefWidthProperty().bind(this.vbox.widthProperty());
        this.txtA.prefWidthProperty().bind(this.vbox.widthProperty());
        this.txtB.prefWidthProperty().bind(this.vbox.widthProperty());
        this.txtC.prefWidthProperty().bind(this.vbox.widthProperty());
        this.txtD.prefWidthProperty().bind(this.vbox.widthProperty());

        //Hide Update button
        this.btnUpdate.setVisible(false);

        //load category
        try {
            this.cbCategories.getItems().addAll(Utils.getCategories());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        //load question
        try {
            this.loadQuestion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        //select row on TableView
        this.tbQuestion.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(r -> {
                this.btnUpdate.setVisible(true);
                Question q = this.tbQuestion.getSelectionModel().getSelectedItem();
                this.txtContent.setText(q.getContent());
                this.txtA.setText(q.getChoices().get(0).getContent());
                this.txtB.setText(q.getChoices().get(1).getContent());
                this.txtC.setText(q.getChoices().get(2).getContent());
                this.txtD.setText(q.getChoices().get(3).getContent());
                this.rdoA.setSelected(q.getChoices().get(0).isCorrect());
                this.rdoB.setSelected(q.getChoices().get(1).isCorrect());
                this.rdoC.setSelected(q.getChoices().get(2).isCorrect());
                this.rdoD.setSelected(q.getChoices().get(3).isCorrect());
                try {
                    this.cbCategories.getSelectionModel().select(Utils.getCategoryById(q.getCategoryId()));
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            });
            return row;
        });
    }

    public void addQuestionHandler(ActionEvent e) {
        String questionId = UUID.randomUUID().toString();

        Question q = new Question(questionId, this.txtContent.getText(),
                this.cbCategories.getSelectionModel().getSelectedItem().getId());

        ArrayList<Choice> choices = new ArrayList<>();
        choices.add(new Choice(UUID.randomUUID().toString(), this.txtA.getText(),
                this.rdoA.isSelected(), questionId));
        choices.add(new Choice(UUID.randomUUID().toString(), this.txtB.getText(),
                this.rdoB.isSelected(), questionId));
        choices.add(new Choice(UUID.randomUUID().toString(), this.txtC.getText(),
                this.rdoC.isSelected(), questionId));
        choices.add(new Choice(UUID.randomUUID().toString(), this.txtD.getText(),
                this.rdoD.isSelected(), questionId));

        try {
            Utils.addQuestion(q,choices);

            this.tbQuestion.getItems().clear();
            this.tbQuestion.setItems(FXCollections.observableArrayList(Utils.getQuestions("")));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Add question successful!");
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Add question failed!" + ex.getMessage());
            alert.show();
        }
    }

    public void updateQuestionHandler(ActionEvent event) {
        Question q = this.tbQuestion.getSelectionModel().getSelectedItem();
        if (q != null) {
            try {
                Utils.updateQuestion(q, (ArrayList<Choice>) q.getChoices());

                this.tbQuestion.getItems().clear();
                this.tbQuestion.setItems(FXCollections.observableArrayList(Utils.getQuestions("")));

                Utils.getAlertInfo("Update question successful!", Alert.AlertType.INFORMATION).show();
            } catch (SQLException e) {
                Utils.getAlertInfo("Update question failed: " + e.getMessage(), Alert.AlertType.ERROR).show();
            }
        }
    }

    public void resetHandler(ActionEvent event) {
        this.txtContent.setText("");
        this.txtA.setText("");
        this.txtB.setText("");
        this.txtC.setText("");
        this.txtD.setText("");
        this.rdoA.setSelected(false);
        this.rdoB.setSelected(false);
        this.rdoC.setSelected(false);
        this.rdoD.setSelected(false);
        this.cbCategories.getSelectionModel().select(null);
        this.tbQuestion.getSelectionModel().select(null);
    }

    private void loadQuestion() throws SQLException {
        TableColumn clContent = new TableColumn("Question content");
        clContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        clContent.setPrefWidth(200);

        TableColumn clChoice = new TableColumn("Choices");
        clChoice.setCellValueFactory(new PropertyValueFactory<>("choiceView"));

        TableColumn clAction = new TableColumn();
        clAction.setCellFactory(p -> {
                Button btn = new Button("Delete");
                btn.setOnAction(et -> {
                    Alert confirm = Utils.getAlertInfo("Are you sure to delete this question?",
                            Alert.AlertType.CONFIRMATION);
                    confirm.showAndWait().ifPresent(res -> {
                        if (res == ButtonType.OK) {
                            TableCell cl = (TableCell)((Button)et.getSource()).getParent();
                            Question q = (Question)cl.getTableRow().getItem();
                            try {
                                Utils.deleteQuestion(q.getId());

                                this.tbQuestion.getItems().clear();
                                this.tbQuestion.setItems(FXCollections.observableArrayList(Utils.getQuestions("")));

                                Utils.getAlertInfo("Delete question successful!",
                                        Alert.AlertType.INFORMATION).show();
                            } catch (SQLException e) {
                                Utils.getAlertInfo("Delete question failed!" + e.getMessage(),
                                        Alert.AlertType.INFORMATION).show();

                                System.err.println(e.getMessage());
                            }
                        }
                    });
                });

                TableCell cell = new TableCell();
                cell.setGraphic(btn);
                return cell;
        });
        this.tbQuestion.getColumns().addAll(clContent, clChoice, clAction);
        this.tbQuestion.setItems(FXCollections.observableArrayList(Utils.getQuestions("")));
    }
}
