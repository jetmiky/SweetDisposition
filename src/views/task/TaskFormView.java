package views.task;

import interfaces.ITaskController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.User;
import repositories.UserRepository;
import views.BaseView;

import java.util.List;

public class TaskFormView extends BaseView {

    private ITaskController controller;
    private ObservableList<User> staffList = FXCollections.observableArrayList(); // ObservableList for staff dropdown

    public TaskFormView(ITaskController controller) {
        this.controller = controller;
    }

    public Scene render() {
        // Input fields
        TextField titleField = new TextField();
        titleField.setPromptText("Task Title");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Task Description");

        // Dropdown for staff selection
        ComboBox<User> staffDropdown = new ComboBox<>();
        staffDropdown.setPromptText("Loading staff...");

        // Asynchronously load staff list
        loadStaffList(staffDropdown);

        // Labels
        Label titleLabel = new Label("Title");
        Label descriptionLabel = new Label("Description");
        Label staffLabel = new Label("Assign to");

        // Error message
        Text errorText = new Text("");

        // Add Task Button
        Button button = new Button("Add Task");
        button.setOnAction(e -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            User selectedStaff = staffDropdown.getValue();

            // Validate input
            if (title.isEmpty() || description.isEmpty()) {
                showAlert("Error", "Title and Description cannot be empty.", Alert.AlertType.ERROR);
                return;
            }
            if (selectedStaff == null) {
                showAlert("Error", "Please select a staff member to assign the task.", Alert.AlertType.ERROR);
                return;
            }

            try {
                // Call store method in controller
                this.controller.store(title, description, selectedStaff.getId());
                
                // Show success alert
                showAlert("Success", "Task successfully added!", Alert.AlertType.INFORMATION);

                // Redirect ke halaman daftar tugas
                screen().redirect("tasks.index.manager");
            } catch (Exception error) {
                showAlert("Error", "Failed to add task: " + error.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // Layout using GridPane
        GridPane container = new GridPane();
        container.setPadding(new Insets(16));
        container.setHgap(12);
        container.setVgap(12);

        // Add elements to layout
        container.add(titleLabel, 0, 0);
        container.add(titleField, 1, 0);
        container.add(descriptionLabel, 0, 1);
        container.add(descriptionField, 1, 1);
        container.add(staffLabel, 0, 2);
        container.add(staffDropdown, 1, 2);
        container.add(button, 0, 3);
        container.add(errorText, 0, 4);
        GridPane.setColumnSpan(errorText, 2);

        return new Scene(container, 400, 300);
    }

    private void loadStaffList(ComboBox<User> staffDropdown) {
        Task<List<User>> task = new Task<>() {
            @Override
            protected List<User> call() throws Exception {
                return UserRepository.getInstance().getStaffList();
            }
        };

        task.setOnSucceeded(e -> {
            staffList.setAll(task.getValue());
            staffDropdown.setItems(staffList);
            staffDropdown.setPromptText("Assign to Staff");
        });

        task.setOnFailed(e -> {
            staffDropdown.setPromptText("Failed to load staff.");
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }

    // Utility method for showing alert
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
