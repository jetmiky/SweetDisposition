package views.task;

import interfaces.ITaskController;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.Task;
import models.User;
import views.BaseView;

public class TaskShowStaffView extends BaseView {

	private ITaskController controller;
	private Task task;
	private User manager;
	private User staff;
	
	public TaskShowStaffView(ITaskController controller, Task task, User manager, User staff) {
		this.controller = controller;
		this.task = task;
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
		
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> {
			screen().redirect("tasks.index.staff");
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
	
		return container;
	}

	
}
