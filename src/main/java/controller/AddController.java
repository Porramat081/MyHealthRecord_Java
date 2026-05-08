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

public class AddController {

    private Model model;
    private Stage stage;
    private Runnable onSaved;
    private Stage parentStage;

    @FXML
    private Button clear;
    @FXML
    private Button add;
    @FXML
    private Label message;
    @FXML
    private TextArea note;
    @FXML
    private TextField lowerBP;
    @FXML
    private TextField upperBP;
    @FXML
    private TextField weight;
    @FXML
    private TextField temperature;

    public AddController(Stage parentStage, Model model) {
        this.stage = new Stage();
        this.model = model;
        this.parentStage = parentStage;
    }

    @FXML
    public void initialize() throws SQLException {
        add.setOnAction(event -> {
            String validateResult = ValidateHealthRecord.validateEditRecord(weight.getText(), temperature.getText(), upperBP.getText(), lowerBP.getText(), note.getText());
            if (validateResult.isEmpty()) {
                try {
                    boolean result = model.getHealthRecordDao().createHealthRecord(model.getCurrentUser().getUsername(), Float.parseFloat(weight.getText()), Float.parseFloat(temperature.getText()), Integer.parseInt(upperBP.getText()), Integer.parseInt(lowerBP.getText()),note.getText());
                    if(result){
                        message.setText("Add new record successfully");
                        message.setTextFill(Color.GREEN);
                        this.clearInput();
                        this.onSaved.run();

                    }else{
                        message.setText("Add new record unsuccessfully");
                        message.setTextFill(Color.RED);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    message.setText(e.getMessage());
                    message.setTextFill(Color.RED);
                }
            }else{
                message.setText(validateResult);
                message.setTextFill(Color.RED);
            }
        });

        clear.setOnAction(event -> {
            this.clearInput();
        });
    }

    public void setOnSaved(Runnable callback) {
        this.onSaved = callback;
    }

    public void clearInput(){
        weight.setText("");
        temperature.setText("");
        upperBP.setText("");
        lowerBP.setText("");
        note.setText("");
        message.setText("");
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
