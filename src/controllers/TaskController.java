package controllers;

import exceptions.FormException;
import interfaces.ITaskController;
import javafx.scene.layout.Pane;
import models.Task;
import models.User;
import views.task.TaskIndexManagerView;
import views.task.TaskIndexStaffView;

public class TaskController extends BaseController implements ITaskController {

	private static TaskController instance;

	public static TaskController getInstance() {
		if (instance == null) {
			instance = new TaskController();
		}

		return instance;
	}

	@Override
	public Pane managerIndex() {
		return new TaskIndexManagerView(this).render(); 
	}

	@Override
	public Pane staffIndex() {
		return new TaskIndexStaffView(this).render();
	}

	@Override
	public void store(String title, String description, User staff) throws FormException {
		if (title.isBlank())
			throw new FormException("Title cannot be empty");
		if (description.isBlank())
			throw new FormException("Description cannot be empty");
		if (staff == null || !staff.exists())
			throw new FormException("Please select a staff");
		
		User manager = auth().user();
		Task task = new Task(manager.getId(), staff.getId(), title, description);

		db().tasks().save(task);
	}
	
	@Override
	public void delete(Task task) throws FormException {
		if (task == null)
			throw new FormException("Please select a task to delete");

		db().tasks().delete(task);
	}

}
