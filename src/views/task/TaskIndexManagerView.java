package views.task;

import exceptions.FormException;
import interfaces.ITaskController;

import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Task;
import views.BaseView;
import views.components.AlertComponent;

public class TaskIndexManagerView extends BaseView {

	private ITaskController controller;
	private List<Task> tasks;

	public TaskIndexManagerView(ITaskController controller, List<Task> tasks) {
		this.controller = controller;
		this.tasks = tasks;
	}

	public Pane render() {
		BorderPane bar = new BorderPane();
		Text title = new Text("Manage Tasks");
		title.setFont(new Font(24));

		Button createTaskButton = new Button("Create Task");
		createTaskButton.setOnAction(e -> screen().redirect("tasks.create"));

		bar.setLeft(title);
		bar.setRight(createTaskButton);
		
		TableView<Task> table = new TableView<>(FXCollections.observableArrayList(tasks));
		TableColumn<Task, Integer> taskIdColumn = new TableColumn<>("ID");
		TableColumn<Task, String> taskTitleColumn = new TableColumn<>("Title");
		TableColumn<Task, String> taskDescriptionColumn = new TableColumn<>("Description");
		TableColumn<Task, Boolean> taskCompletedColumn = new TableColumn<>("Task Completed");
		TableColumn<Task, Date> taskCreatedAtColumn = new TableColumn<>("Created At");		

		taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		taskCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("isCompleted"));
		taskCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		
		table.getColumns().addAll(taskIdColumn, taskTitleColumn, taskDescriptionColumn, taskCompletedColumn, taskCreatedAtColumn);

		HBox buttons = new HBox();
		buttons.setSpacing(8);

		Button viewTaskButton = new Button("View Task Detail");
		viewTaskButton.setOnAction(e -> {
			try {
				Task task = table.getSelectionModel().getSelectedItem();
				if (task == null)
					throw new FormException("Please select a task to view");

				state().set("task", task);
				screen().redirect("tasks.show.manager");
			} catch (FormException error) {
				AlertComponent.error("Failed", error.getMessage());
			}
		});

		Button deleteTaskButton = new Button("Delete Selected Task");
		deleteTaskButton.setOnAction(e -> {
			try {
				Task task = table.getSelectionModel().getSelectedItem();

				controller.delete(task);
				table.getItems().remove(task);

				AlertComponent.success("Success", "Task successfully deleted");
			} catch (FormException error) {
				AlertComponent.error("Failed", error.getMessage());
			}
		});

		buttons.getChildren().addAll(viewTaskButton, deleteTaskButton);

		VBox container = new VBox(10);
		container.setPadding(new Insets(16));
		container.setSpacing(16);
		container.getChildren().addAll(bar, table, buttons);

		return container;
	}

}
