package views.task;

import java.util.ArrayList;
import java.util.List;
import interfaces.ITaskController;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Task;
import views.BaseView;

public class TaskIndexManagerView extends BaseView {

	private ITaskController controller;

	public TaskIndexManagerView(ITaskController controller) {
		this.controller = controller;
	}

	public Scene render() {
		HBox bar = new HBox();
		
		Text title = new Text("Available Tasks");
		title.setFont(new Font(24));
		
		Button button = new Button("Create Task");
		button.setOnAction(e -> {
			screen().redirect("tasks.create");
		});
		
		bar.getChildren().addAll(title, button);
		
		List<Task> tasks = (ArrayList<Task>) this.data.getOrDefault("tasks", new ArrayList<Task>());		
		
		TableView<Task> table = new TableView<>(FXCollections.observableArrayList(tasks));
		TableColumn<Task, String> taskIdColumn = new TableColumn<>("ID");
		TableColumn<Task, String> taskTitleColumn = new TableColumn<>("Title");
		TableColumn<Task, String> taskDescriptionColumn = new TableColumn<>("Description");
		
		taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		table.getColumns().addAll(taskIdColumn, taskTitleColumn, taskDescriptionColumn);
		
		VBox container = new VBox();
		container.getChildren().addAll(bar, table);	

		return new Scene(container);
	}

}
