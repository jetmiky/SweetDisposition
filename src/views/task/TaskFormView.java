package views.task;

import exceptions.FormException;
import interfaces.ITaskController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.Task;
import models.User;
import views.BaseModal;
import views.components.AlertComponent;

public class TaskFormView extends BaseModal {

    private final ITaskController controller;
    private ObservableList<Task> tasks;
    private ObservableList<User> staffs;

    public TaskFormView(ITaskController controller, ObservableList<Task> tasks) {
        super("Create New Task");
    	
        User user = auth().user();
        
    	this.controller = controller;
    	this.tasks = tasks;
    	this.staffs = FXCollections.observableArrayList(user.getStaffs());
    } 

    @Override
	public Scene render() {
        ImageView imageView = new ImageView();
        
        try {
            Image image = new Image(getClass().getResource("/resources/task.png").toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
        } catch (Exception e) {
            System.out.println("Error loading image");
        }
        
        //formTitle
        Text formTitle = new Text("Tambah Task");
        formTitle.setFont(Font.font("Open Sans", FontWeight.BOLD, 24));
        formTitle.setFill(Color.BLACK);

        // Form Fields
        TextField titleField = new TextField();
        titleField.setPromptText("Task Title");
        titleField.setMaxWidth(Double.MAX_VALUE);

        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Task Description");
        descriptionField.setMaxWidth(Double.MAX_VALUE);

        ComboBox<User> staffDropdown = new ComboBox<>();
        staffDropdown.setPromptText("Choose a staff");
        staffDropdown.setItems(this.staffs);
        staffDropdown.setMaxWidth(Double.MAX_VALUE);

        // Labels
        Label titleLabel = new Label("Title\t\t\t:");
        Label descriptionLabel = new Label("Description\t:");
        Label staffLabel = new Label("Assign Task to\t:");

        // Buttons
        Button addButton = new Button("Add Task");
        addButton.setStyle("-fx-background-color: #1E88E5; -fx-font-size: 14px; -fx-text-fill: white; -fx-font-weight: bold;");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            User staff = staffDropdown.getSelectionModel().getSelectedItem();

            try {
                Task task = this.controller.store(title, description, staff);
                
                tasks.add(task);
                AlertComponent.success("Success", "Task successfully created");
                this.close();
            } catch (FormException error) {
                AlertComponent.error("Error", error.getMessage());
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #E53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        cancelButton.setOnAction(e -> {
        	this.close(); // Close the current modal
        });
        
        HBox buttonBar = new HBox(10, addButton, cancelButton);
        buttonBar.setAlignment(Pos.CENTER);

        // Grid Layout for Form Fields
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setAlignment(Pos.CENTER);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        formGrid.getColumnConstraints().addAll(col1, col2);
        
        formGrid.add(titleLabel, 0, 0);
        formGrid.add(titleField, 1, 0);
        formGrid.add(descriptionLabel, 0, 1);
        formGrid.add(descriptionField, 1, 1);
        formGrid.add(staffLabel, 0, 2);
        formGrid.add(staffDropdown, 1, 2);

        // Main Layout
        VBox mainLayout = new VBox(20, imageView, formTitle, formGrid, buttonBar);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #f1f5f9;");

        return new Scene(mainLayout, 400, 600);
    }

}
