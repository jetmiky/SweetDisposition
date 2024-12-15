package views.task;

import java.util.Date;
import java.util.List;

import interfaces.ITaskController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.Progress;
import models.Task;
import models.User;
import views.BaseView;

public class TaskShowManagerView extends BaseView {

	private ITaskController controller;
	private Task task;
	private ObservableList<Progress> progresses;
	private User manager;
	private User staff;
	
	public TaskShowManagerView(ITaskController controller, Task task, List<Progress> progresses, User manager, User staff) {
		this.controller = controller;
		this.task = task;
		this.progresses = FXCollections.observableArrayList(progresses);
		this.manager = manager;
		this.staff = staff;
	}

	@Override
	public Pane render() {
		Text manager = new Text("Manager");
		Text title = new Text("Title");
		Text description = new Text("Description");
		Text staff = new Text("Staff");
		
		Text managerName = new Text(this.manager.getName());
		Text taskTitle = new Text(this.task.getTitle());
		Text taskDescription = new Text(this.task.getDescription());
		Text staffName = new Text(this.staff.getName());
		
		TableView<Progress> table = new TableView<>(this.progresses);
		
		TableColumn<Progress, String> idColumn = new TableColumn<>("ID");
		TableColumn<Progress, String> descriptionColumn = new TableColumn<>("Description");
		TableColumn<Progress, Boolean> completedColumn = new TableColumn<>("Task Completed");
		TableColumn<Progress, Date> dateColumn = new TableColumn<>("Created At");
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		completedColumn.setCellValueFactory(new PropertyValueFactory<>("isCompleted"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		
		table.getColumns().addAll(idColumn, descriptionColumn, completedColumn, dateColumn);
		
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> {
			screen().redirect("tasks.index.manager");
		});
		
		GridPane container = new GridPane();
		container.add(manager, 0, 0);
		container.add(managerName, 1, 0);
		container.add(title, 0, 1);
		container.add(taskTitle, 1, 1);
		container.add(description, 0, 2);
		container.add(taskDescription, 1, 2);
		container.add(staff, 0, 3);
		container.add(staffName, 1, 3);
		container.add(cancelButton, 0, 4);
		container.add(table, 0, 5);
	
		return container;
	}

	
}
