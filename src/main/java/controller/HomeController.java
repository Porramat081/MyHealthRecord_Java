package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.HealthRecord;
import model.Model;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
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
    @FXML
    private ScrollPane tablePane;
    @FXML
    private MenuItem signout;
    @FXML
    private MenuItem editprofile;

	
	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	// Add your code to complete the functionality of the program
	@FXML
    public void initialize() throws SQLException {
        User currentUser = model.getCurrentUser();

        ArrayList<HealthRecord> hrs = model.getHealthRecordDao().getHealthRecords(currentUser.getUsername());

        model.setCurrentHealthRecords(hrs);

        signout.setOnAction(event -> {
            model.resetState();
            stage.close();
            parentStage.show();
        });

        editprofile.setOnAction(event -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditView.fxml"));
                ProfileController profileController = new ProfileController(stage,model);
                loader.setController(profileController);
                Pane root = loader.load();
                profileController.showStage(root);

            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Record Table
        TableView<HealthRecord> table = new TableView<>();
        TableColumn<HealthRecord , Integer> recordIndex = new TableColumn<>("No");
        TableColumn<HealthRecord , String> bloodPressure = new TableColumn<>("Blood Pressure");
        TableColumn<HealthRecord , String> weight = new TableColumn<>("Weight");
        TableColumn<HealthRecord , String> temperature = new TableColumn<>("Temperature");
        TableColumn<HealthRecord , Timestamp> createdAt = new TableColumn<>("Created");
        TableColumn<HealthRecord , Timestamp> editedAt = new TableColumn<>("Edited");
        TableColumn<HealthRecord , String> note = new TableColumn<>("Note");

        bloodPressure.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        editedAt.setCellValueFactory(new PropertyValueFactory<>("editedAt"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));

        table.getColumns().add(recordIndex);
        table.getColumns().add(bloodPressure);
        table.getColumns().add(weight);
        table.getColumns().add(temperature);
        table.getColumns().add(createdAt);
        table.getColumns().add(editedAt);
        table.getColumns().add(note);

        recordIndex.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(table.getItems().indexOf(cellData.getValue()) + 1)
        );

        createdAt.setCellFactory(col -> new TableCell<>(){
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            @Override
            public void updateItem(Timestamp item , boolean empty){
                super.updateItem(item,empty);
                if (empty || item == null){
                    setText(null);
                }else{
                    setText(item.toLocalDateTime().format(formatter));
                }
            }
        });

        editedAt.setCellFactory(col -> new TableCell<>(){
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            @Override
            public void updateItem(Timestamp item , boolean empty){
                super.updateItem(item,empty);
                if (empty || item == null){
                    setText(null);
                }else{
                    setText(item.toLocalDateTime().format(formatter));
                }
            }
        });

        note.setCellFactory(col -> new TableCell<>(){
            @Override
            public void updateItem(String item , boolean empty){
                super.updateItem(item , empty);
                if(empty || item == null){
                    setText(null);
                } else if (item.length() > 15) {
                    setText(item.substring(0,15) + "...");
                }else{
                    setText(item);
                }
            }
        });

        for (HealthRecord h : model.getCurrentHealthRecords()){
            System.out.println(h.getNote());
            table.getItems().add(h);
        }

        note.prefWidthProperty().bind(table.widthProperty().multiply(0.34));

        recordIndex.setStyle("-fx-alignment: CENTER;");
        bloodPressure.setStyle("-fx-alignment: CENTER;");
        weight.setStyle("-fx-alignment: CENTER;");
        temperature.setStyle("-fx-alignment: CENTER;");
        createdAt.setStyle("-fx-alignment: CENTER;");
        editedAt.setStyle("-fx-alignment: CENTER;");

        tablePane.setFitToWidth(true);
        tablePane.setFitToHeight(true);

        tablePane.setContent(table);

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
