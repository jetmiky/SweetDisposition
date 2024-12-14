package controllers;

import exceptions.FormException;
import interfaces.ITaskController;
import java.util.List;
import javafx.scene.Scene;
import models.Task;
import models.User;
import views.task.TaskFormView;
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
	
	public Scene manager() {
		User user = auth().user();
		List<Task> tasks = db().tasks().select().where("manager_id", user.getId()).get();
		
		TaskIndexManagerView view = new TaskIndexManagerView(this);
		view.data("tasks", tasks);
		
		return view.render();
	}
	
	public Scene staff() {
		User user = auth().user();
		List<Task> tasks = db().tasks().select().where("staff_id", user.getId()).get();
		
		TaskIndexStaffView view = new TaskIndexStaffView(this);
		view.data("tasks", tasks);
		
		return view.render();
	}

	public Scene create() {
		
		TaskFormView view = new TaskFormView(this);
		return view.render();
	}

	@Override
	public void store(String title, String description) throws FormException {
		Task task = new Task(1, 1, title, description);

		db().tasks().save(task);

		screen().redirect("tasks.index");
	}

}
