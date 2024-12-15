package controllers;

import exceptions.FormException;
import interfaces.ITaskController;
import java.util.List;
import javafx.scene.layout.Pane;
import models.Progress;
import models.Task;
import models.User;
import views.task.TaskFormView;
import views.task.TaskIndexManagerView;
import views.task.TaskIndexStaffView;
import views.task.TaskShowManagerView;
import views.task.TaskShowStaffView;

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
		User user = auth().user();
		List<Task> tasks = db().tasks().select().where("manager_id", user.getId()).get();

		return new TaskIndexManagerView(this, tasks).render();
	}

	@Override
	public Pane staffIndex() {
		User user = auth().user();
		List<Task> tasks = db().tasks().select().where("staff_id", user.getId()).get();
		
		return new TaskIndexStaffView(this, tasks).render();
	}

	@Override
	public Pane create() {
		User manager = auth().user();
		List<User> staffs = db().users().getStaffs(manager);

		return new TaskFormView(this, staffs).render();
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

		screen().redirect("tasks.index.manager");
	}
	
	@Override
	public Pane managerShow() {
		Task task = (Task) state().get("task");
		List<Progress> progresses = db().progresses().getProgresses(task);
		User manager = db().users().get(task.getManagerId());
		User staff = db().users().get(task.getStaffId());
		
		return new TaskShowManagerView(this, task, progresses, manager, staff).render();
	}
	
	@Override
	public Pane staffShow() {
		Task task = (Task) state().get("task");
		List<Progress> progresses = db().progresses().select().where("task_id", task.getId()).get();
		User manager = db().users().get(task.getManagerId());
		User staff = db().users().get(task.getStaffId());
		
		return new TaskShowStaffView(this, task, progresses, manager, staff).render();
	}

	@Override
	public void delete(Task task) throws FormException {
		if (task == null)
			throw new FormException("Please select a task to delete");

		db().tasks().delete(task);
	}

}
