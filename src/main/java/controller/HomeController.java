package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class HomeController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private MenuItem viewProfile; // Corresponds to the Menu item "viewProfile" in HomeView.fxml
	@FXML
	private MenuItem updateProfile; // // Corresponds to the Menu item "updateProfile" in HomeView.fxml
    @FXML
    private Text welcome;


	
	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	// Add your code to complete the functionality of the program
	@FXML
    public void initialize() throws SQLException {
        User currentUser = model.getCurrentUser();

        model.getHealthRecordDao().createHealthRecord(currentUser.getUsername(), 45.5f,36.7f,120,80,"test add record 1");
        model.getHealthRecordDao().createHealthRecord(currentUser.getUsername(), 45.5f,36.7f,120,80,"test add record 2");

        ArrayList<HealthRecord> hrs = model.getHealthRecordDao().getHealthRecords(currentUser.getUsername());

        model.setCurrentHealthRecords(hrs);

        for (HealthRecord h : model.getCurrentHealthRecords()){
            System.out.println(h.getNote());
        }

        String welcomeMessage = welcome.getText() + " " + currentUser.getFirstname()+" "+currentUser.getLastname();
        welcome.setText(welcomeMessage);
    }


	

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 1280, 800);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();
	}
}
