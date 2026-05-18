package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import utils.Exporter;

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
    private MenuItem exportmenu;
    @FXML
    private Text welcome;
    @FXML
    private ScrollPane tablePane;
    @FXML
    private MenuItem signout;
    @FXML
    private MenuItem editprofile;
    @FXML
    private Pane addpane;

    private TableView<HealthRecord> table;

	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	// Add your code to complete the functionality of the program
	@FXML
    public void initialize() throws SQLException, IOException {
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
                profileController.setOnSaved(()->{
                    String welcomeMessage = "Welcome , "+ currentUser.getFirstname()+" "+currentUser.getLastname();
                    this.welcome.setText(welcomeMessage);
                });
                loader.setController(profileController);
                Pane root = loader.load();
                profileController.showStage(root);

            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Add record pane
        FXMLLoader subloader = new FXMLLoader(getClass().getResource("/view/AddRecord.fxml"));
        AddController addController = new AddController(stage,model);
        addController.setOnSaved(()-> {
            try {
                this.refreshTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        subloader.setController(addController);
        Pane subPane = subloader.load();

        addpane.getChildren().add(subPane);

        exportmenu.setOnAction(event-> {
            try {
                Exporter.exportCSV(model.getCurrentHealthRecords());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Record Table
        table = new TableView<>();
        TableColumn<HealthRecord , Integer> recordIndex = new TableColumn<>("No");
        TableColumn<HealthRecord , String> bloodPressure = new TableColumn<>("Blood Pressure (mmHg)");
        TableColumn<HealthRecord , String> weight = new TableColumn<>("Weight (kg)");
        TableColumn<HealthRecord , String> temperature = new TableColumn<>("Temperature (celsius)");
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

        table.setRowFactory(tableview -> {
            TableRow<HealthRecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                HealthRecord rowData = row.getItem();
                try {
                    this.openEditRecord(rowData);
                } catch (IOException e) {
                    e.printStackTrace();
                    this.showError("Error" , e.getMessage());
                }
            });
            return row;
        });

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
                } else if (item.length() > 30) {
                    System.out.println(item);
                    setText(item.substring(0,30) + "...");
                }else{
                    setText(item);
                }
            }
        });

        for (HealthRecord h : model.getCurrentHealthRecords()){
            table.getItems().add(h);
        }

        note.prefWidthProperty().bind(table.widthProperty().multiply(0.21));

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

    public void openEditRecord(HealthRecord hr) throws IOException {
        if(hr != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditRecordView.fxml"));
            RecordController recordController = new RecordController(stage,model,hr);
            recordController.setOnSaved(()-> {
                try {
                    this.refreshTable();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            loader.setController(recordController);
            Pane root = loader.load();
            recordController.showStage(root);
        }
    }

    public void refreshTable() throws SQLException {
        ObservableList<HealthRecord> data = FXCollections.observableArrayList(
                model.getHealthRecordDao().getHealthRecords(model.getCurrentUser().getUsername())
        );
        table.setItems(data);
    }

    public static void showError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 1280, 800);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();
	}
}
