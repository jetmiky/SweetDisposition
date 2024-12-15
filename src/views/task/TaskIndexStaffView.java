package views.task;

import java.util.List;

import exceptions.FormException;
import interfaces.ITaskController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class TaskIndexStaffView extends BaseView {

	private ITaskController controller;
	private ObservableList<Task> tasks;

	public TaskIndexStaffView(ITaskController controller, List<Task> tasks) {
		this.controller = controller;
		this.tasks = FXCollections.observableArrayList(tasks);
	}

	public Pane render() {
		HBox bar = new HBox();
		
		Text title = new Text("Available Tasks");
		title.setFont(new Font(24));
		
		bar.getChildren().addAll(title);
		
		TableView<Task> table = new TableView<>(tasks);
		TableColumn<Task, String> taskIdColumn = new TableColumn<>("ID");
		TableColumn<Task, String> taskTitleColumn = new TableColumn<>("Title");
		TableColumn<Task, String> taskDescriptionColumn = new TableColumn<>("Description");
		
		taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		table.getColumns().addAll(taskIdColumn, taskTitleColumn, taskDescriptionColumn);
		
		Button viewTaskButton = new Button("View Selected Task");
		viewTaskButton.setOnAction(e -> {
			try {
				Task task = table.getSelectionModel().getSelectedItem();
				if (task == null)
					throw new FormException("Please select a task to view");

				state().set("task", task);
				screen().redirect("tasks.show.staff");
			} catch (FormException error) {
				AlertComponent.error("Failed", error.getMessage());
			}
		});
		
		HBox buttons = new HBox();
		buttons.setSpacing(8);
		buttons.getChildren().addAll(viewTaskButton);
		
		VBox container = new VBox();
		container.getChildren().addAll(bar, table, buttons);	

		return container;
	}

}
