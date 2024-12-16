package views.task;

import interfaces.ITaskController;
import java.net.URISyntaxException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Progress;
import models.Task;
import models.User;
import views.BaseModal;

public class TaskShowManagerView extends BaseModal {

    private ITaskController controller;
    private Task task;
    private ObservableList<Progress> progresses;
    private User manager;
    private User staff;

    public TaskShowManagerView(ITaskController controller, Task task) {
        super("Task Detail");
    	
    	this.controller = controller;
        this.task = task;
        this.progresses = FXCollections.observableArrayList(task.getProgresses());
        this.manager = task.getManager();
        this.staff = task.getStaff();
    }

    @Override
    public Scene render() {
        ImageView imageView = new ImageView();
        
        try {
            Image image = new Image(getClass().getResource("/resources/bannerwork.png").toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(600);  // Adjust the size of the image
            imageView.setFitHeight(100);  // Adjust the size of the image
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
        } catch (Exception e) {
            System.out.println("Error loading image");
        }

        // Labels for task details
        Text managerLabel = new Text("Manager");
        Text titleLabel = new Text("Task Title");
        Text descriptionLabel = new Text("Task Description");
        Text staffLabel = new Text("Staff");

        // Details for task and users
        Text managerName = new Text(this.manager.getName());
        Text taskTitle = new Text(this.task.getTitle());
        Text taskDescription = new Text(this.task.getDescription());
        Text staffName = new Text(this.staff.getName());

        // Styling the text
        managerLabel.setFont(Font.font("Arial", 14));
        titleLabel.setFont(Font.font("Arial", 14));
        descriptionLabel.setFont(Font.font("Arial", 14));
        staffLabel.setFont(Font.font("Arial", 14));

        managerName.setFont(Font.font("Arial", 16));
        taskTitle.setFont(Font.font("Arial", 16));
        taskDescription.setFont(Font.font("Arial", 16));
        staffName.setFont(Font.font("Arial", 16));

        // Style the labels
        managerLabel.setFill(Color.DARKSLATEGRAY);
        titleLabel.setFill(Color.DARKSLATEGRAY);
        descriptionLabel.setFill(Color.DARKSLATEGRAY);
        staffLabel.setFill(Color.DARKSLATEGRAY);

        // Add white background to task details (Text fields)
        managerName.setStyle("-fx-background-color: white;");
        taskTitle.setStyle("-fx-background-color: white;");
        taskDescription.setStyle("-fx-background-color: white;");
        staffName.setStyle("-fx-background-color: white;");

        // Progress Table
        TableView<Progress> table = new TableView<>(this.progresses);
        TableColumn<Progress, String> idColumn = new TableColumn<>("ID");
        TableColumn<Progress, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Progress, Boolean> completedColumn = new TableColumn<>("Completed");
        TableColumn<Progress, String> dateColumn = new TableColumn<>("Created At");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        completedColumn.setCellValueFactory(new PropertyValueFactory<>("isCompleted"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        table.getColumns().addAll(idColumn, descriptionColumn, completedColumn, dateColumn);

        // Back button
        Button cancelButton = new Button("Back");
        cancelButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;");
        cancelButton.setOnAction(e -> this.close());

        // Styling cancelButton with icon
        ImageView ivCancelButton = new ImageView();
        try {
            Image cancelButtonIcon = new Image(getClass().getResource("/resources/icons/back.png").toURI().toString());
            ivCancelButton.setImage(cancelButtonIcon);
            ivCancelButton.setFitWidth(20);  // Adjust the size of the icon
            ivCancelButton.setFitHeight(20);
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }

        // Add the icon to the button
        cancelButton.setGraphic(ivCancelButton);

        // Button container
        HBox buttons = new HBox(8, cancelButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setStyle("-fx-padding: 10px;");

        // Details Grid Layout
        GridPane details = new GridPane();
        details.setVgap(16);
        details.setHgap(8);
        details.add(managerLabel, 0, 0);
        details.add(managerName, 1, 0);
        details.add(titleLabel, 0, 1);
        details.add(taskTitle, 1, 1);
        details.add(descriptionLabel, 0, 2);
        details.add(taskDescription, 1, 2);
        details.add(staffLabel, 0, 3);
        details.add(staffName, 1, 3);
        details.setStyle("-fx-padding: 10px;");

        // Style the table for better readability
        table.setStyle("-fx-border-color: #BDBDBD; -fx-border-width: 1px;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Main container
        VBox container = new VBox(16);
        container.setPadding(new Insets(16));
        container.getChildren().addAll(imageView, details, table, buttons);
        container.setAlignment(Pos.TOP_CENTER);

        // Background color and layout styling
        container.setStyle("-fx-background-color: #f0f0f0; -fx-border-radius: 10px; -fx-padding: 20px;");

        return new Scene(container, 600, 600);
    }

}
