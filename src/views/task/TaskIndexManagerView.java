package views.task;

import exceptions.FormException;
import interfaces.ITaskController;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
		VBox container = new VBox(10);

		HBox bar = new HBox(10);
		Text title = new Text("Manage Tasks");
		title.setFont(new Font(24));

		Button createTaskButton = new Button("Create Task");
		createTaskButton.setOnAction(e -> screen().redirect("tasks.create"));

		bar.getChildren().addAll(title, createTaskButton);

		TableView<Task> table = new TableView<>(FXCollections.observableArrayList(tasks));
		TableColumn<Task, Integer> taskIdColumn = new TableColumn<>("ID");
		TableColumn<Task, String> taskTitleColumn = new TableColumn<>("Title");
		TableColumn<Task, String> taskDescriptionColumn = new TableColumn<>("Description");

		taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		table.getColumns().addAll(taskIdColumn, taskTitleColumn, taskDescriptionColumn);

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

		container.getChildren().addAll(bar, table, deleteTaskButton);

		return container;
	}

}
