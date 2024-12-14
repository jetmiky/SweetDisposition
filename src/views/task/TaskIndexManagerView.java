package views.task;

import interfaces.ITaskController;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Task;
import views.BaseView;

import java.util.ArrayList;
import java.util.List;

public class TaskIndexManagerView extends BaseView {

    private ITaskController controller;

    public TaskIndexManagerView(ITaskController controller) {
        this.controller = controller;
    }

    public Scene render() {
        VBox container = new VBox(10);

        // Title Bar
        HBox bar = new HBox(10);
        Text title = new Text("Manage Tasks");
        title.setFont(new Font(24));

        Button createTaskButton = new Button("Create Task");
        createTaskButton.setOnAction(e -> screen().redirect("tasks.create"));

        bar.getChildren().addAll(title, createTaskButton);

        // Table for displaying tasks
        List<Task> tasks = (ArrayList<Task>) this.data.getOrDefault("tasks", new ArrayList<Task>());
        TableView<Task> table = new TableView<>(FXCollections.observableArrayList(tasks));
        TableColumn<Task, Integer> taskIdColumn = new TableColumn<>("ID");
        TableColumn<Task, String> taskTitleColumn = new TableColumn<>("Title");
        TableColumn<Task, String> taskDescriptionColumn = new TableColumn<>("Description");

        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(taskIdColumn, taskTitleColumn, taskDescriptionColumn);

        // Delete Task Button
        Button deleteTaskButton = new Button("Delete Selected Task");
        deleteTaskButton.setOnAction(e -> {
            Task selectedTask = table.getSelectionModel().getSelectedItem();

            if (selectedTask == null) {
                showAlert("Please select a task to delete.", false);
                return;
            }

            Integer taskId = selectedTask.getId();
            if (taskId == null) {
                showAlert("Selected task does not have a valid ID.", false);
                return;
            }

            boolean success = controller.deleteTask(taskId);
            if (success) {
                table.getItems().remove(selectedTask);
                showAlert("Task deleted successfully!", true);
            } else {
                showAlert("Failed to delete task. Please try again.", false);
            }
        });

        // Add all components to container
        container.getChildren().addAll(bar, table, deleteTaskButton);

        return new Scene(container, 800, 600);
    }

    private void showAlert(String message, boolean isSuccess) {
        Alert alert = new Alert(isSuccess ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(isSuccess ? "Success" : "Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
