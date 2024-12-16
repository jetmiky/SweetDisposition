package views.task;

import java.util.Date;
import interfaces.ITaskController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Progress;
import models.Task;
import models.User;
import views.BaseModal;
import views.progresses.ProgressFormView;

public class TaskShowStaffView extends BaseModal {

	private ITaskController controller;
	private Task task;
	private ObservableList<Progress> progresses;
	private User manager;
	private User staff;

	public TaskShowStaffView(ITaskController controller, Task task) {
		super("Task Detail");
		
		this.controller = controller;
		this.task = task;
		this.progresses = FXCollections.observableArrayList(task.getProgresses());
		this.manager = task.getManager();
		this.staff = task.getStaff();
	}

	@Override
	public Scene render() {
		Text manager = new Text("Manager");
		Text title = new Text("Title");
		Text description = new Text("Description");
		Text staff = new Text("Staff");

		Text managerName = new Text(this.manager.getName());
		Text taskTitle = new Text(this.task.getTitle());
		Text taskDescription = new Text(this.task.getDescription());
		Text staffName = new Text(this.staff.getName());
		
		Text progressesTitle = new Text("Progresses");
		progressesTitle.setFont(new Font("Arial Black", 20));

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

		Button updateProgressButton = new Button("Tambah Progress");
		updateProgressButton.setOnAction(e -> {
			new ProgressFormView(task).show();
			table.refresh();
		});

		Button cancelButton = new Button("Back");
		cancelButton.setOnAction(e -> this.close());
		
		HBox buttons = new HBox();
		buttons.setSpacing(8);

		if (!this.task.isCompleted()) {
			buttons.getChildren().add(updateProgressButton);
		}

		buttons.getChildren().add(cancelButton);
		
		GridPane details = new GridPane();
		details.setVgap(16);
		details.setHgap(8);
		details.add(manager, 0, 0);
		details.add(managerName, 1, 0);
		details.add(title, 0, 1);
		details.add(taskTitle, 1, 1);
		details.add(description, 0, 2);
		details.add(taskDescription, 1, 2);
		details.add(staff, 0, 3);
		details.add(staffName, 1, 3);
		details.add(progressesTitle, 0, 4);
		
		VBox container = new VBox();
		container.setPadding(new Insets(16));
		container.setSpacing(16);
		container.getChildren().addAll(details, table, buttons);
		
		return new Scene(container, 800, 500);
	}

}
