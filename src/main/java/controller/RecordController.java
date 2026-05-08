package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import utils.FormatterString;
import utils.ValidateHealthRecord;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

public class RecordController {
    private Model model;
    private Stage stage;
    private HealthRecord selectedRecord;
    private Stage parentStage;
    private Runnable onSaved;

    @FXML
    private TextField weight;
    @FXML
    private TextField temperature;
    @FXML
    private TextField upperBP;
    @FXML
    private TextField lowerBP;
    @FXML
    private TextArea note;
    @FXML
    private Button edit;
    @FXML
    private Button cancel;
    @FXML
    private Label message;

    public RecordController(Stage parentStage, Model model, HealthRecord inputRecord) {
        this.stage = new Stage();
        this.model = model;
        this.selectedRecord = inputRecord;
        this.parentStage = parentStage;
    }

    @FXML
    public void initialize() throws SQLException {
        weight.setText(String.valueOf(this.selectedRecord.getWeight()));
        temperature.setText(String.valueOf(this.selectedRecord.getTemperature()));
        HashMap<String, Integer> bp = FormatterString.splitBP(this.selectedRecord.getBloodPressure());

        upperBP.setText(String.valueOf(bp.get("upper")));
        lowerBP.setText(String.valueOf(bp.get("lower")));

        note.setText(this.selectedRecord.getNote());

        cancel.setOnAction(event -> {
            try {
                boolean result = model.getHealthRecordDao().deleteHealthRecord(this.selectedRecord.getHealthRecordId(),model.getCurrentUser().getUsername());
                if(result){
                    message.setText("Delete record successfully");
                    message.setTextFill(Color.GREEN);
                    this.onSaved.run();
                    this.stage.close();
                }else{
                    message.setText("Delete record unsuccessfully");
                    message.setTextFill(Color.RED);
                }
            } catch (SQLException e) {
                message.setText(e.getMessage());
                message.setTextFill(Color.RED);
            }
        });

        edit.setOnAction(event -> {
            //validate edit health record
            String validateResult = ValidateHealthRecord.validateEditRecord(weight.getText(), temperature.getText(), upperBP.getText(), lowerBP.getText(), note.getText());
            //edit
            if (validateResult.isEmpty()) {
                this.selectedRecord.setWeight(Float.parseFloat(weight.getText()));
                this.selectedRecord.setTemperature(Float.parseFloat(temperature.getText()));
                this.selectedRecord.setBloodPressure(FormatterString.combineBP(Integer.parseInt(upperBP.getText()), Integer.parseInt(lowerBP.getText())));
                this.selectedRecord.setNote(note.getText());
                Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());
                this.selectedRecord.setEditedAt(timestampNow);

                try {
                    boolean result = this.model.getHealthRecordDao().editHealthRecord(this.selectedRecord, model.getCurrentUser().getUsername());
                    if (result) {
                        message.setText("Update record successfully");
                        message.setTextFill(Color.GREEN);

                        if (this.onSaved != null) {
                            this.onSaved.run();
                        }

                    } else {
                        message.setText("Update record unsuccessfully");
                        message.setTextFill(Color.RED);
                    }
                } catch (SQLException e) {
                    message.setText(e.getMessage());
                    message.setTextFill(Color.RED);
                }
            } else {
                message.setText(validateResult);
                message.setTextFill(Color.RED);
            }
        });

    }

    public void setOnSaved(Runnable callback) {
        this.onSaved = callback;
    }

    public void showStage(Parent root) {
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Edit Health Record");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
