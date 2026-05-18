package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;
import utils.FormatterString;
import utils.ValidateSignup;

public class SignupController {
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button createUser;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
	@FXML
	private Button close;
	@FXML
	private Label status;
    @FXML
    private TextField confirmpassword;
    @FXML
    private Label passwordLabel;
	
	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public SignupController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
	public void initialize() {
        passwordLabel.setText(FormatterString.generatePasswordRule());
		createUser.setOnAction(event -> {
            String inputUsername = username.getText();
            String inputPassword = password.getText();
            String inputFirstname = firstname.getText();
            String inputLastname = lastname.getText();
            String inputConfirmedPassword = confirmpassword.getText();

            String validateResult = ValidateSignup.validateForm(inputUsername , inputPassword , inputFirstname ,inputLastname , inputConfirmedPassword);
			if (validateResult.isEmpty()) {
				User user = new User();
				try {
					user = model.getUserDao().createUser(inputUsername , inputPassword , inputFirstname ,inputLastname);

                    if (user != null) {
						status.setText("Created " + user.getUsername());
						status.setTextFill(Color.GREEN);
                        model.setCurrentUser(user);
                        parentStage.show();
                        stage.close();
					} else {
						status.setText("Cannot create user");
						status.setTextFill(Color.RED);
					}
				} catch (SQLException e) {
                    if(e.getMessage().startsWith("[SQLITE_CONSTRAINT_PRIMARYKEY]")){
                        status.setText("This username is already used,\n Please try other username.");
                    }else{
                        status.setText(e.getMessage());
                    }
					status.setTextFill(Color.RED);
				}
				
			} else {
				status.setText(validateResult);
				status.setTextFill(Color.RED);
			}
		});

		close.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 694, 517);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Sign up");
		stage.show();
	}
}
