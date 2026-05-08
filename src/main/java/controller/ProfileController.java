package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import model.User;
import utils.ValidateSignup;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ProfileController {
    private Model model;
    private Stage stage;

    @FXML
    private Button cancel;
    @FXML
    private Button edit;
    @FXML
    private Text username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmpassword;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private Text message;

    public ProfileController(Stage parentStage , Model model){
        this.stage = new Stage();
        this.model = model;
    }

    @FXML
    public void initialize() throws SQLException{
        User currentUser = model.getCurrentUser();

        username.setText(currentUser.getUsername());
        password.setText(currentUser.getPassword());
        firstname.setText(currentUser.getFirstname());
        lastname.setText(currentUser.getLastname());
        confirmpassword.setText(currentUser.getPassword());

        cancel.setOnAction(event -> {
            stage.close();
        });
        edit.setOnAction(event -> {
            String validateEdit = ValidateSignup.validateForm(username.getText(),password.getText(),firstname.getText(),lastname.getText(),confirmpassword.getText());
            if(!validateEdit.isEmpty()){
                message.setText(validateEdit);
                message.setFill(Color.RED);
            }else{
                message.setText("");
                Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());
                currentUser.setPassword(password.getText());
                currentUser.setFirstname(firstname.getText());
                currentUser.setLastname(lastname.getText());
                currentUser.setEditedAt(timestampNow);
                try{
                    boolean result = model.getUserDao().editUser(currentUser);
                    if(result){
                        message.setText("Update Profile Successfully");
                        message.setFill(Color.GREEN);

                    }else{
                        message.setText("Update Profile Unsuccessfully");
                        message.setFill(Color.RED);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    message.setText(e.getMessage());
                    message.setFill(Color.RED);
                }
            }
        });
    }

    public void showStage(Parent root) {
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Edit Profile");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
